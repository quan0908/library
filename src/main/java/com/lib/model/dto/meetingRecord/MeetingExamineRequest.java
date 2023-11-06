package com.lib.model.dto.meetingRecord;

import lombok.Data;

import java.io.Serializable;

/**
 * 会议审核请求
 */
@Data
public class MeetingExamineRequest implements Serializable {
    /**
     * 会议记录id
     */
    private Long id;

    /**
     * 0-未审核 1-同意 2-不同意
     */
    private Integer status;
    private static final long serialVersionUID = 1L;
}
