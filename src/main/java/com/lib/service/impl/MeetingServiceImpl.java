package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.mapper.MeetingMapper;
import com.lib.model.dto.meeting.MeetingAddRequest;
import com.lib.model.dto.meeting.MeetingQueryRequest;
import com.lib.model.dto.meeting.MeetingUpdateRequest;
import com.lib.model.entity.Meeting;
import com.lib.model.entity.MeetingRoom;
import com.lib.model.entity.User;
import com.lib.model.vo.MeetingRoomVO;
import com.lib.model.vo.MeetingVO;
import com.lib.model.vo.UserVO;
import com.lib.service.MeetingRoomService;
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
* @author quan
*/
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting>
    implements MeetingService {
    @Resource
    private UserService userService;

    @Resource
    private MeetingRoomService meetingRoomService;

    /**
     * 获取会议查询条件(根据会议name,会议id,会议创建人ID,会议室id)
     * @param meetingQueryRequest 会议查询请求
     * @return
     */
    @Override
    public QueryWrapper<Meeting> getQueryWrapper(MeetingQueryRequest meetingQueryRequest) {
        if (meetingQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long id = meetingQueryRequest.getId();
        String meetingName = meetingQueryRequest.getName();
        String account = meetingQueryRequest.getAccount();
        User user = new User();
        Long creatorId = user.getId();
        if(StringUtils.isNotBlank(account)){
            //根据用户账号查找userId
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account",account);

            user = userService.getOne(queryWrapper);
            if(user == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            creatorId = user.getId();
        }
        Long meetingRoomId = meetingQueryRequest.getMeetingRoomId();
        String sortField = meetingQueryRequest.getSortField();
        String sortOrder = meetingQueryRequest.getSortOrder();

        QueryWrapper<Meeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(meetingName), "name", meetingName);
        queryWrapper.eq(id != null,"id",id);
        queryWrapper.eq(StringUtils.isNotBlank(account),"creatorId",creatorId);
        queryWrapper.eq(meetingRoomId != null,"meetingRoomId",meetingRoomId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<MeetingVO> getMeetingVO(List<Meeting> meetingList) {
        if (CollectionUtils.isEmpty(meetingList)) {
            return new ArrayList<>();
        }
        return meetingList.stream().map(this::getMeetingVO).collect(Collectors.toList());
    }

    @Override
    public MeetingVO getMeetingVO(Meeting meeting) {
        if (meeting == null) {
            return null;
        }

        Long creatorId = meeting.getCreatorId();
        Long meetingRoomId = meeting.getMeetingRoomId();
        if(creatorId == null || meetingRoomId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //转成userVO
        User user = userService.getById(creatorId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        //转成meetingRoomVO
        MeetingRoom meetingRoom = meetingRoomService.getById(meetingRoomId);
        if(meetingRoom == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        MeetingRoomVO meetingRoomVO = MeetingRoomVO.objToVo(meetingRoom);

        MeetingVO meetingVO = new MeetingVO();
        BeanUtils.copyProperties(meeting, meetingVO);
        meetingVO.setMeetingRoomVO(meetingRoomVO);
        meetingVO.setCreatorVO(userVO);
        return meetingVO;
    }

    /**
     * 添加会议
     * @param meetingAddRequest 会议添加请求
     * @return
     */
    @Override
    public boolean addMeeting(MeetingAddRequest meetingAddRequest, HttpServletRequest request) {
        //参数校验
        if(meetingAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String meetingName = meetingAddRequest.getName();
        Long meetingRoomId = meetingAddRequest.getMeetingRoomId();
        Date startTime = meetingAddRequest.getStartTime();
        Date endTime = meetingAddRequest.getEndTime();

        if(StringUtils.isAnyBlank(meetingName)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if(meetingRoomId == null || startTime == null || endTime == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if(startTime.getTime() > endTime.getTime()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Meeting meeting = new Meeting();
        meeting.setName(meetingName);
        meeting.setMeetingRoomId(meetingRoomId);
        meeting.setStartTime(startTime);
        meeting.setEndTime(endTime);
        User user = userService.getLoginUser(request);
        meeting.setCreatorId(user.getId());
        return this.save(meeting);
    }



    /**
     * 修改会议
     * @param meetingUpdateRequest 会议修改请求
     * @return
     */
    @Override
    public boolean updateMeeting(MeetingUpdateRequest meetingUpdateRequest) {
        if(meetingUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingRoomId = meetingUpdateRequest.getMeetingRoomId();
        String name = meetingUpdateRequest.getName();
        Date startTime = meetingUpdateRequest.getStartTime();
        Date endTime = meetingUpdateRequest.getEndTime();

        if(StringUtils.isAnyBlank(name)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if(meetingRoomId == null || startTime == null || endTime == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //开始时间大于结束时间，参数异常
        if(startTime.getTime() > endTime.getTime()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Meeting meeting = new Meeting();
        //新数据覆盖旧数据
        BeanUtils.copyProperties(meetingUpdateRequest,meeting);
        return this.updateById(meeting);
    }

    /**
     * 删除会议
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteMeeting(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingId = deleteRequest.getId();
        return this.removeById(meetingId);
    }
}




