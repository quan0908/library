package com.lib.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.BookConstant;
import com.lib.constant.CommonConstant;
import com.lib.constant.MeetingRoomConstant;
import com.lib.exception.BusinessException;
import com.lib.exception.ThrowUtils;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordAddRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomUpdateRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordAddRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordQueryRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordUpdateRequest;
import com.lib.model.entity.*;
import com.lib.model.vo.MeetingRoomBorrowRecordVO;
import com.lib.model.vo.MeetingRoomVO;
import com.lib.model.vo.UserVO;
import com.lib.service.MeetingRoomBorrowRecordService;
import com.lib.mapper.MeetingRoomBorrowRecordMapper;
import com.lib.service.MeetingRoomService;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author vv
*/
@Service
public class MeetingRoomBorrowRecordServiceImpl extends ServiceImpl<MeetingRoomBorrowRecordMapper, MeetingRoomBorrowRecord>
    implements MeetingRoomBorrowRecordService{
    @Resource
    private MeetingRoomService meetingRoomService;

    @Resource
    private UserService userService;

    /**
     * 获取查询条件
     * @param meetingRoomBorrowRecordQueryRequest 借会议室查询记录请求
     * @return
     */
    @Override
    public QueryWrapper<MeetingRoomBorrowRecord> getQueryWrapper(MeetingRoomBorrowRecordQueryRequest meetingRoomBorrowRecordQueryRequest) {
        if (meetingRoomBorrowRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long meetingRoomId = meetingRoomBorrowRecordQueryRequest.getMeetingRoomId();
        Long userId = meetingRoomBorrowRecordQueryRequest.getUserId();
        String sortField = meetingRoomBorrowRecordQueryRequest.getSortField();
        String sortOrder = meetingRoomBorrowRecordQueryRequest.getSortOrder();
        Integer status = meetingRoomBorrowRecordQueryRequest.getStatus();
        QueryWrapper<MeetingRoomBorrowRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(meetingRoomId != null, "meetingRoomId", meetingRoomId);
        queryWrapper.eq(status != null, "status", status);
        queryWrapper.eq(userId != null, "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取全部申请会议室记录VO
     * @param meetingRoomBorrowRecordList
     * @return
     */
    @Override
    public List<MeetingRoomBorrowRecordVO> getMeetingRoomBorrowRecordVO(List<MeetingRoomBorrowRecord> meetingRoomBorrowRecordList) {
        if (CollectionUtils.isEmpty(meetingRoomBorrowRecordList)) {
            return new ArrayList<>();
        }
        return meetingRoomBorrowRecordList.stream().map(this::getMeetingRoomBorrowRecordVO).collect(Collectors.toList());
    }

    /**
     * 获取借会议室记录VO
     * @param meetingRoomBorrowRecord
     * @return
     */
    @Override
    public MeetingRoomBorrowRecordVO getMeetingRoomBorrowRecordVO(MeetingRoomBorrowRecord meetingRoomBorrowRecord) {


        if(meetingRoomBorrowRecord == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //获取meetingRoomVO
        Long meetingRoomId = meetingRoomBorrowRecord.getMeetingRoomId();
        MeetingRoom meetingRoom = meetingRoomService.getById(meetingRoomId);
        if(meetingRoom == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        MeetingRoomVO meetingRoomVO = MeetingRoomVO.objToVo(meetingRoom);

        MeetingRoomBorrowRecordVO meetingRoomBorrowRecordVO = new MeetingRoomBorrowRecordVO();
        //获取userVO
        Long userId = meetingRoomBorrowRecord.getUserId();
        meetingRoomBorrowRecordVO.setId(meetingRoomBorrowRecord.getId());
        String checkUserId = meetingRoomBorrowRecord.getCheckUserId();

        if(checkUserId != null){
            User checkUser = userService.getById(checkUserId);
            UserVO checkUserVO = UserVO.objToVo(checkUser);
            meetingRoomBorrowRecordVO.setCheckUser(checkUserVO);
        }

        User user = userService.getById(userId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);


        meetingRoomBorrowRecordVO.setMeetingRoomVO(meetingRoomVO);
        meetingRoomBorrowRecordVO.setUserVO(userVO);
        meetingRoomBorrowRecordVO.setStartTime(meetingRoomBorrowRecord.getStartTime());
        meetingRoomBorrowRecordVO.setEndTime(meetingRoomBorrowRecord.getEndTime());
        meetingRoomBorrowRecordVO.setStatus(meetingRoomBorrowRecord.getStatus());
        return meetingRoomBorrowRecordVO;
    }

    /**
     * 添加借会议室记录
     * @param meetingRoomBorrowRecordAddRequest 添加借会议室记录请求
     * @return
     */
    @Override
    public boolean addMeetingRoomBorrowRecord(MeetingRoomBorrowRecordAddRequest meetingRoomBorrowRecordAddRequest,
                                              HttpServletRequest request) {
        //参数校验
        if(meetingRoomBorrowRecordAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingRoomId = meetingRoomBorrowRecordAddRequest.getMeetingRoomId();
        String username = meetingRoomBorrowRecordAddRequest.getUsername();
        String idCard = meetingRoomBorrowRecordAddRequest.getIdCard();
        Date startTime = meetingRoomBorrowRecordAddRequest.getStartTime();
        Date endTime = meetingRoomBorrowRecordAddRequest.getEndTime();

        MeetingRoom meetingRoom = meetingRoomService.getById(meetingRoomId);

        if(meetingRoomId == null || startTime == null|| endTime == null||meetingRoom == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(meetingRoom.getIsEmpty() != 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该会议室以借出");
        }

        if(StringUtils.isAnyBlank(idCard,username)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //对用户进行校验
        User user = userService.getLoginUser(request);
        if(!user.getIdCard().equals(idCard)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(!user.getUsername().equals(username)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        //TODO 对于时间的校验，应当更为严格
        if(startTime.getTime() - System.currentTimeMillis() < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if(endTime.getTime() - startTime.getTime() < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //加入数据库
        MeetingRoomBorrowRecord meetingRoomBorrowRecord = new MeetingRoomBorrowRecord();
        meetingRoomBorrowRecord.setMeetingRoomId(meetingRoomId);
        meetingRoomBorrowRecord.setEndTime(endTime);
        meetingRoomBorrowRecord.setStartTime(startTime);
        meetingRoomBorrowRecord.setUserId(user.getId());
        return this.save(meetingRoomBorrowRecord);
    }

    /**
     *
     * @param meetingRoomBorrowRecordUpdateRequest 会议室修改请求
     * @return
     */
    @Override
    public boolean updateMeetingRoomBorrowRecord(MeetingRoomBorrowRecordUpdateRequest meetingRoomBorrowRecordUpdateRequest) {

        if(meetingRoomBorrowRecordUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long meetingRoomBorrowRecordId = meetingRoomBorrowRecordUpdateRequest.getId();
        if(meetingRoomBorrowRecordId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //如果发现是通过，则会将会议室设置为不为空
        if(meetingRoomBorrowRecordUpdateRequest.getStatus() == 1){
            Long id = meetingRoomBorrowRecordUpdateRequest.getId();
            MeetingRoomBorrowRecord borrowRecord = this.getById(id);
            MeetingRoom meetingRoom = new MeetingRoom();
            meetingRoom.setIsEmpty(1);
            meetingRoom.setId(borrowRecord.getMeetingRoomId());
            meetingRoomService.updateById(meetingRoom);
        }
        MeetingRoomBorrowRecord meetingRoomBorrowRecord= new MeetingRoomBorrowRecord();
        BeanUtil.copyProperties(meetingRoomBorrowRecordUpdateRequest,meetingRoomBorrowRecord);
        return this.updateById(meetingRoomBorrowRecord);
    }


    /**
     * 删除借会议室记录
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteMeetingRoomBorrowRecord(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingRoomId = deleteRequest.getId();
        return this.removeById(meetingRoomId);
    }

    @Override
    public Long getMeetingRoomBorrowOwner(String meetingRoomId) {
        QueryWrapper<MeetingRoomBorrowRecord> meetingRoomBorrowRecordQueryWrapper = new QueryWrapper<>();
        meetingRoomBorrowRecordQueryWrapper.eq("meetingRoomId",meetingRoomId);
        meetingRoomBorrowRecordQueryWrapper.eq("status",1);
        meetingRoomBorrowRecordQueryWrapper.orderByAsc("createTime");
        List<MeetingRoomBorrowRecord> list = this.list(meetingRoomBorrowRecordQueryWrapper);
        return list.get(0).getUserId();
    }
}




