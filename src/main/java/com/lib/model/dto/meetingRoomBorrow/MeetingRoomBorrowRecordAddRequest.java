package com.lib.model.dto.meetingRoomBorrow;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    /**
     * 借会议室天数
     */
    private Date borrowTime;

    private static final long serialVersionUID = 1L;
}
