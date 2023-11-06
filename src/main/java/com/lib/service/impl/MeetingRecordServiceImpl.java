package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.mapper.MeetingRecordMapper;
import com.lib.model.dto.meetingRecord.*;
import com.lib.model.entity.Meeting;
import com.lib.model.entity.MeetingRecord;
import com.lib.model.entity.User;
import com.lib.model.vo.MeetingRecordVO;
import com.lib.model.vo.MeetingVO;
import com.lib.model.vo.UserVO;
import com.lib.service.MeetingRecordService;
import com.lib.service.MeetingService;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author am
* @description 针对表【meetingRecord_record】的数据库操作Service实现
* @createDate 2023-11-04 14:02:41
*/
@Service
public class MeetingRecordServiceImpl extends ServiceImpl<MeetingRecordMapper, MeetingRecord>
    implements MeetingRecordService {
    @Resource
    private UserService userService;

    @Resource
    private MeetingService meetingService;

    /**
     * 获取会议查询条件(根据会议id，参与人账号,会议记录状态)
     * @param meetingRecordQueryRequest 会议查询请求
     * @return
     */
    @Override
    public QueryWrapper<MeetingRecord> getQueryWrapper(MeetingRecordQueryRequest meetingRecordQueryRequest) {
        if (meetingRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long meetingId = meetingRecordQueryRequest.getMeetingId();
        Integer status = meetingRecordQueryRequest.getStatus();
        String account = meetingRecordQueryRequest.getAccount();
        User user = new User();
        Long participantId = user.getId();
        if(StringUtils.isNotBlank(account)){
            //根据用户账号查找userId
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account",account);

            user = userService.getOne(queryWrapper);
            if(user == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            participantId = user.getId();
        }

        String sortField = meetingRecordQueryRequest.getSortField();
        String sortOrder = meetingRecordQueryRequest.getSortOrder();

        QueryWrapper<MeetingRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(meetingId != null,"meetingId",meetingId);
        queryWrapper.eq(StringUtils.isNotBlank(account),"participantId",participantId);
        queryWrapper.eq(status != null,"status",status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<MeetingRecordVO> getMeetingRecordVO(List<MeetingRecord> meetingRecordList) {
        if (CollectionUtils.isEmpty(meetingRecordList)) {
            return new ArrayList<>();
        }
        return meetingRecordList.stream().map(this::getMeetingRecordVO).collect(Collectors.toList());
    }

    @Override
    public MeetingRecordVO getMeetingRecordVO(MeetingRecord meetingRecord) {
        if (meetingRecord == null) {
            return null;
        }

        Long meetingId = meetingRecord.getMeetingId();
        Long participantId = meetingRecord.getParticipantId();
        if(participantId == null || meetingId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //转成userVO
        User user = userService.getById(participantId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        //转成meetingVO
        Meeting meeting = meetingService.getById(meetingId);
        if(meeting == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        MeetingVO meetingVO = meetingService.getMeetingVO(meeting);
        MeetingRecordVO meetingRecordVO = new MeetingRecordVO();
        BeanUtils.copyProperties(meetingRecord, meetingRecordVO);
        meetingRecordVO.setMeetingVO(meetingVO);
        meetingRecordVO.setParticipantVO(userVO);
        return meetingRecordVO;
    }

    /**
     * 添加会议
     * @param meetingRecordAddRequest 会议添加请求
     * @return
     */
    @Override
    public boolean addMeetingRecord(MeetingRecordAddRequest meetingRecordAddRequest) {
        //参数校验
        if(meetingRecordAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingId = meetingRecordAddRequest.getMeetingId();
        Long participantId = meetingRecordAddRequest.getParticipantId();

        if(meetingId == null || participantId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        MeetingRecord meetingRecord = new MeetingRecord();
        meetingRecord.setMeetingId(meetingId);
        meetingRecord.setParticipantId(participantId);
        return this.save(meetingRecord);
    }

    /**
     * 申请加入会议
     * @param meetingApplyJoinRequest
     * @param request
     * @return
     */
    @Override
    public boolean applyJoinMeeting(MeetingApplyJoinRequest meetingApplyJoinRequest, HttpServletRequest request) {
        if(meetingApplyJoinRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long meetingId = meetingApplyJoinRequest.getMeetingId();
        if(meetingId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Meeting meeting = meetingService.getById(meetingId);
        if(meeting == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //判断用户是否已经提交了申请
        QueryWrapper<MeetingRecord> meetingRecordQueryWrapper = new QueryWrapper<>();
        meetingRecordQueryWrapper.eq("meetingId",meeting.getId());
        Long participantId = userService.getLoginUser(request).getId();
        meetingRecordQueryWrapper.eq("participantId",participantId);

        if(this.getOne(meetingRecordQueryWrapper) != null){
            throw new BusinessException(ErrorCode.APPLY_ERROR);
        }

        MeetingRecord meetingRecord = new MeetingRecord();
        meetingRecord.setMeetingId(meetingId);
        meetingRecord.setParticipantId(participantId);
        return this.save(meetingRecord);
    }

    /**
     * 审核加入会议
     * @param meetingExamineRequest
     * @param request
     * @return
     */
    @Override
    public boolean examineMeeting(MeetingExamineRequest meetingExamineRequest, HttpServletRequest request) {
        //参数校验
        if(meetingExamineRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = meetingExamineRequest.getId();
        Integer status = meetingExamineRequest.getStatus();
        if(id == null || status == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        MeetingRecord meetingRecord = this.getById(id);
        if(meetingRecord == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long meetingId = meetingRecord.getMeetingId();
        Meeting meeting = meetingService.getById(meetingId);
        Long creatorId = meeting.getCreatorId();
        User user = userService.getLoginUser(request);
        //判断当前用户有无操作权限,只有会议发起人才有权限
        if(!user.getId().equals(creatorId)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        MeetingRecordUpdateRequest meetingRecordUpdateRequest = new MeetingRecordUpdateRequest();
        BeanUtils.copyProperties(meetingRecord,meetingRecordUpdateRequest);
        meetingRecordUpdateRequest.setStatus(meetingExamineRequest.getStatus());
        return this.updateMeetingRecord(meetingRecordUpdateRequest);
    }

    /**
     * 修改会议
     * @param meetingRecordUpdateRequest 会议修改请求
     * @return
     */
    @Override
    public boolean updateMeetingRecord(MeetingRecordUpdateRequest meetingRecordUpdateRequest) {
        if(meetingRecordUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingId = meetingRecordUpdateRequest.getMeetingId();

        if(meetingId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        MeetingRecord meetingRecord = new MeetingRecord();
        BeanUtils.copyProperties(meetingRecordUpdateRequest,meetingRecord);
        return this.updateById(meetingRecord);
    }

    /**
     * 删除会议
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteMeetingRecord(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingRecordId = deleteRequest.getId();
        return this.removeById(meetingRecordId);
    }
}




