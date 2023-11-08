package com.lib.model.dto.meetingRecord;

import lombok.Data;

import java.io.Serializable;

/**
 * 申请加入会议请求
 */
@Data
public class MeetingApplyJoinRequest implements Serializable {
    /**
     * 会议室id
     */
    private Long meetingRoomId;
    private static final long serialVersionUID = 1L;
}
