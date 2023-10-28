package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.model.dto.meetingRoom.MeetingRoomAddRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomQueryRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomUpdateRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomAddRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomQueryRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomUpdateRequest;
import com.lib.model.entity.MeetingRoom;
import com.lib.model.entity.MeetingRoom;
import com.lib.model.vo.MeetingRoomVO;
import com.lib.model.vo.MeetingRoomVO;
import com.lib.service.MeetingRoomService;
import com.lib.mapper.MeetingRoomMapper;
import com.lib.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author vv
*/
@Service
public class MeetingRoomServiceImpl extends ServiceImpl<MeetingRoomMapper, MeetingRoom>
    implements MeetingRoomService{

    /**
     * 获取会议室查询条件(根据会议室name)
     * @param meetingRoomQueryRequest 会议室查询请求
     * @return
     */
    @Override
    public QueryWrapper<MeetingRoom> getQueryWrapper(MeetingRoomQueryRequest meetingRoomQueryRequest) {
        if (meetingRoomQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        String meetingRoomName = meetingRoomQueryRequest.getMeetingRoomName();
        String sortField = meetingRoomQueryRequest.getSortField();
        String sortOrder = meetingRoomQueryRequest.getSortOrder();

        QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(meetingRoomName), "name", meetingRoomName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<MeetingRoomVO> getMeetingRoomVO(List<MeetingRoom> meetingRoomList) {
        if (CollectionUtils.isEmpty(meetingRoomList)) {
            return new ArrayList<>();
        }
        return meetingRoomList.stream().map(this::getMeetingRoomVO).collect(Collectors.toList());
    }

    @Override
    public MeetingRoomVO getMeetingRoomVO(MeetingRoom meetingRoom) {
        if (meetingRoom == null) {
            return null;
        }
        MeetingRoomVO meetingRoomVO = new MeetingRoomVO();
        BeanUtils.copyProperties(meetingRoom, meetingRoomVO);
        return meetingRoomVO;
    }

    /**
     * 添加会议室
     * @param meetingRoomAddRequest 会议室添加请求
     * @return
     */
    @Override
    public boolean addMeetingRoom(MeetingRoomAddRequest meetingRoomAddRequest) {
        //参数校验
        if(meetingRoomAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String meetingRoomName = meetingRoomAddRequest.getMeetingRoomName();
        if(StringUtils.isAnyBlank(meetingRoomName)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setName(meetingRoomName);
        return this.save(meetingRoom);
    }

    /**
     * 修改会议室
     * @param meetingRoomUpdateRequest 会议室修改请求
     * @return
     */
    @Override
    public boolean updateMeetingRoom(MeetingRoomUpdateRequest meetingRoomUpdateRequest) {
        if(meetingRoomUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingRoomId = meetingRoomUpdateRequest.getMeetingRoomId();
        String meetingRoomName = meetingRoomUpdateRequest.getMeetingRoomName();

        if(StringUtils.isAnyBlank(meetingRoomName)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if(meetingRoomId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //查询出原来的数据
        QueryWrapper<MeetingRoom> meetingRoomQueryWrapper = new QueryWrapper<>();
        meetingRoomQueryWrapper.eq("id", meetingRoomId);
        MeetingRoom meetingRoom = this.getOne(meetingRoomQueryWrapper);
        if(meetingRoom == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //新数据覆盖旧数据
        BeanUtils.copyProperties(meetingRoomUpdateRequest,meetingRoom);
        return this.updateById(meetingRoom);
    }

    /**
     * 删除会议室
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteMeetingRoom(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long meetingRoomId = deleteRequest.getId();
        return this.removeById(meetingRoomId);
    }
}




