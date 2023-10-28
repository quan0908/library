package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.constant.MeetingRoomConstant;
import com.lib.exception.BusinessException;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordAddRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordQueryRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordUpdateRequest;
import com.lib.model.entity.MeetingRoom;
import com.lib.model.entity.MeetingRoomBorrowRecord;
import com.lib.model.entity.User;
import com.lib.model.vo.MeetingRoomBorrowRecordVO;
import com.lib.model.vo.MeetingRoomVO;
import com.lib.model.vo.UserVO;
import com.lib.service.MeetingRoomBorrowRecordService;
import com.lib.mapper.MeetingRoomBorrowRecordMapper;
import com.lib.service.MeetingRoomService;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

        QueryWrapper<MeetingRoomBorrowRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(meetingRoomId != null, "meetingRoomId", meetingRoomId);
        queryWrapper.like(userId != null, "userId", userId);
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

        //获取userVO
        Long userId = meetingRoomBorrowRecord.getUserId();
        User user = userService.getById(userId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        MeetingRoomBorrowRecordVO meetingRoomBorrowRecordVO = new MeetingRoomBorrowRecordVO();
        meetingRoomBorrowRecordVO.setMeetingRoomVO(meetingRoomVO);
        meetingRoomBorrowRecordVO.setUserVO(userVO);
        meetingRoomBorrowRecordVO.setMeetingRoomRecordId(meetingRoomBorrowRecord.getId());
        meetingRoomBorrowRecordVO.setBorrowTime(meetingRoomBorrowRecord.getBorrowTime());
        return meetingRoomBorrowRecordVO;
    }

    /**
     * 添加借会议室记录
     * @param meetingRoomBorrowRecordAddRequest 添加借会议室记录请求
     * @return
     */
    @Override
    public boolean addMeetingRoomBorrowRecord(MeetingRoomBorrowRecordAddRequest meetingRoomBorrowRecordAddRequest) {
        //参数校验
        if(meetingRoomBorrowRecordAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingRoomId = meetingRoomBorrowRecordAddRequest.getMeetingRoomId();
        Date borrowTime = meetingRoomBorrowRecordAddRequest.getBorrowTime();
        //获取当前id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = userService.getLoginUser(request);

        //判断这本会议室在数据库中是否存在
        if(meetingRoomService.getById(meetingRoomId) == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //判断选的时间是否早于当前
        if(borrowTime.getTime() - System.currentTimeMillis() < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //加入数据库
        MeetingRoomBorrowRecord meetingRoomBorrowRecord = new MeetingRoomBorrowRecord();
        meetingRoomBorrowRecord.setMeetingRoomId(meetingRoomId);
        meetingRoomBorrowRecord.setBorrowTime(borrowTime);
        meetingRoomBorrowRecord.setUserId(user.getId());
        return this.save(meetingRoomBorrowRecord);
    }

    /**
     * 修改借会议室记录(还会议室 只修改了isReturn字段)
     * @param meetingRoomUpdateRequest 会议室修改请求
     * @return
     */
    @Override
    public boolean updateMeetingRoomBorrowRecord(MeetingRoomBorrowRecordUpdateRequest meetingRoomUpdateRequest) {
        if(meetingRoomUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingRoomBorrowRecordId = meetingRoomUpdateRequest.getMeetingRoomBorrowRecordId();
        if(meetingRoomBorrowRecordId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        MeetingRoomBorrowRecord meetingRoomBorrowRecord = this.getById(meetingRoomBorrowRecordId);
        meetingRoomBorrowRecord.setIsReturn(MeetingRoomConstant.RETURN);
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
}




