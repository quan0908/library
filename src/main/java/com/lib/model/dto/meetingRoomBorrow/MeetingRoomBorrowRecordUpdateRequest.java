package com.lib.model.dto.meetingRoomBorrow;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议室记录修改请求
 */
@Data
public class MeetingRoomBorrowRecordUpdateRequest implements Serializable {
    /**
     * 借会议室记录id
     */
    private Long id;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 审核通过
     */
    private Integer status;
    /**
     * 审核人
     */
    private String checkUserId;

    /**
     * 结束时间
     */
    private Date endTime;

    private static final long serialVersionUID = 1L;
}
