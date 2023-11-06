package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.meeting.MeetingAddRequest;
import com.lib.model.dto.meetingRecord.*;
import com.lib.model.entity.MeetingRecord;
import com.lib.model.vo.MeetingRecordVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author quan
*/
public interface MeetingRecordService extends IService<MeetingRecord> {
    /**
     * 获取查询条件
     *
     * @param meetingRecordQueryRequest 会议记录查询请求
     * @return
     */
    QueryWrapper<MeetingRecord> getQueryWrapper(MeetingRecordQueryRequest meetingRecordQueryRequest);

    /**
     * 获取会议信息
     *
     * @param meetingRecordList 会议记录集合
     * @return
     */
    List<MeetingRecordVO> getMeetingRecordVO(List<MeetingRecord> meetingRecordList);

    /**
     * 获取会议记录信息
     *
     * @param meetingRecord
     * @return
     */
    MeetingRecordVO getMeetingRecordVO(MeetingRecord meetingRecord);

    /**
     * 添加会议记录
     * @param meetingRecordAddRequest 会议记录请求
     * @return 是否添加成功
     */
    boolean addMeetingRecord(MeetingRecordAddRequest meetingRecordAddRequest);

    /**
     * 申请加入会议
     * @param meetingApplyJoinRequest 会议申请加入请求
     * @return
     */
    boolean applyJoinMeeting(MeetingApplyJoinRequest meetingApplyJoinRequest, HttpServletRequest request);

    /**
     * 审核加入会议请求
     * @return
     */
    boolean examineMeeting(MeetingExamineRequest meetingExamineRequest,HttpServletRequest request);

    /**
     * 修改会议
     * @param meetingRecordUpdateRequest 会议记录修改请求
     * @return 是否修改成功
     */
    boolean updateMeetingRecord(MeetingRecordUpdateRequest meetingRecordUpdateRequest);

    /**
     * 删除会议
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteMeetingRecord(DeleteRequest deleteRequest);
}
