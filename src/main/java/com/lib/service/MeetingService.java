package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.meeting.MeetingAddRequest;
import com.lib.model.dto.meeting.MeetingQueryRequest;
import com.lib.model.dto.meeting.MeetingUpdateRequest;
import com.lib.model.entity.Meeting;
import com.lib.model.entity.Meeting;
import com.lib.model.vo.MeetingVO;

import java.util.List;

/**
* @author am
* @description 针对表【meeting】的数据库操作Service
* @createDate 2023-11-04 14:02:30
*/
public interface MeetingService extends IService<Meeting> {
    /**
     * 获取查询条件
     *
     * @param meetingQueryRequest 会议查询请求
     * @return
     */
    QueryWrapper<Meeting> getQueryWrapper(MeetingQueryRequest meetingQueryRequest);

    /**
     * 获取全部会议信息
     *
     * @param meetingList 会议集合
     * @return
     */
    List<MeetingVO> getMeetingVO(List<Meeting> meetingList);

    /**
     * 获取会议信息VO
     *
     * @param meeting
     * @return
     */
    MeetingVO getMeetingVO(Meeting meeting);

    /**
     * 添加会议
     * @param meetingAddRequest 会议添加请求
     * @return 是否添加成功
     */
    boolean addMeeting(MeetingAddRequest meetingAddRequest);

    /**
     * 修改图书
     * @param meetingUpdateRequest 会议修改请求
     * @return 是否修改成功
     */
    boolean updateMeeting(MeetingUpdateRequest meetingUpdateRequest);

    /**
     * 删除图书
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteMeeting(DeleteRequest deleteRequest);
}
