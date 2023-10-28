package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordAddRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordQueryRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordUpdateRequest;
import com.lib.model.entity.MeetingRoomBorrowRecord;
import com.lib.model.entity.MeetingRoomBorrowRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.model.vo.MeetingRoomBorrowRecordVO;

import java.util.List;

/**
* @author vv
*/
public interface MeetingRoomBorrowRecordService extends IService<MeetingRoomBorrowRecord> {
    /**
     * 获取查询条件
     *
     * @param meetingRoomQueryRequest 会议室申请记录查询请求
     * @return
     */
    QueryWrapper<MeetingRoomBorrowRecord> getQueryWrapper(MeetingRoomBorrowRecordQueryRequest meetingRoomQueryRequest);

    /**
     * 获取会议室申请记录信息
     *
     * @param meetingRoomList 会议室集合
     * @return
     */
    List<MeetingRoomBorrowRecordVO> getMeetingRoomBorrowRecordVO(List<MeetingRoomBorrowRecord> meetingRoomList);

    /**
     * 获取会议室申请记录信息
     *
     * @param meetingRoom
     * @return
     */
    MeetingRoomBorrowRecordVO getMeetingRoomBorrowRecordVO(MeetingRoomBorrowRecord meetingRoom);

    /**
     * 添加借会议室记录
     * @param meetingRoomAddRequest 借会议室记录请求
     * @return 是否添加成功
     */
    boolean addMeetingRoomBorrowRecord(MeetingRoomBorrowRecordAddRequest meetingRoomAddRequest);

    /**
     * 修改会议室
     * @param meetingRoomUpdateRequest 会议室修改请求
     * @return 是否修改成功
     */
    boolean updateMeetingRoomBorrowRecord(MeetingRoomBorrowRecordUpdateRequest meetingRoomUpdateRequest);

    /**
     * 删除会议室
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteMeetingRoomBorrowRecord(DeleteRequest deleteRequest);
}
