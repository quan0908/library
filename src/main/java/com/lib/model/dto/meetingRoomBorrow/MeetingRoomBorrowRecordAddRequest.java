package com.lib.model.dto.meetingRoomBorrow;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议室记录添加请求
 */
@Data
public class MeetingRoomBorrowRecordAddRequest implements Serializable {
    /**
     * 会议室ID
     */
    private Long meetingRoomId;

    /**
     * 借会议室天数
     */
    private Date borrowTime;

    private static final long serialVersionUID = 1L;
}
