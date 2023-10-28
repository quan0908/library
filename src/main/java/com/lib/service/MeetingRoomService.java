package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomAddRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomQueryRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomUpdateRequest;
import com.lib.model.entity.MeetingRoom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.model.vo.MeetingRoomVO;

import java.util.List;

/**
* @author vv
*/
public interface MeetingRoomService extends IService<MeetingRoom> {
    /**
     * 获取查询条件
     *
     * @param meetingRoomQueryRequest 会议室查询请求
     * @return
     */
    QueryWrapper<MeetingRoom> getQueryWrapper(MeetingRoomQueryRequest meetingRoomQueryRequest);

    /**
     * 获取全部会议室信息
     *
     * @param meetingRoomList 会议室集合
     * @return
     */
    List<MeetingRoomVO> getMeetingRoomVO(List<MeetingRoom> meetingRoomList);

    /**
     * 获取会议室信息VO
     *
     * @param meetingRoom
     * @return
     */
    MeetingRoomVO getMeetingRoomVO(MeetingRoom meetingRoom);

    /**
     * 添加会议室
     * @param meetingRoomAddRequest 会议室添加请求
     * @return 是否添加成功
     */
    boolean addMeetingRoom(MeetingRoomAddRequest meetingRoomAddRequest);

    /**
     * 修改图书
     * @param meetingRoomUpdateRequest 会议室修改请求
     * @return 是否修改成功
     */
    boolean updateMeetingRoom(MeetingRoomUpdateRequest meetingRoomUpdateRequest);

    /**
     * 删除图书
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteMeetingRoom(DeleteRequest deleteRequest);
}
