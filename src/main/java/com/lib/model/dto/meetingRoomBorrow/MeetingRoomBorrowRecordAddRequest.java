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
     * 用户姓名
     */
    private String username;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    private static final long serialVersionUID = 1L;
}
