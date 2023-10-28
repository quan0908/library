package com.lib.model.dto.meetingRoomBorrow;

import lombok.Data;

import java.io.Serializable;

/**
 * 会议室记录修改请求
 */
@Data
public class MeetingRoomBorrowRecordUpdateRequest implements Serializable {
    /**
     * 借书记录id
     */
    private Long meetingRoomBorrowRecordId;

    private static final long serialVersionUID = 1L;
}