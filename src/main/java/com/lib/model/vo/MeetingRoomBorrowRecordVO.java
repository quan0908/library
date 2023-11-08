package com.lib.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author quan
 */
@Data
public class MeetingRoomBorrowRecordVO implements Serializable {
    /**
     * 会议室申请记录id
     */
    private Long id;

    /**
     * 会议室VO
     */
    private MeetingRoomVO meetingRoomVO;

    /**
     * 用户VO
     */
    private UserVO userVO;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;


    /**
     * 审核人id
     */
    private UserVO checkUser;

    /**
     * 0-未审核 1-同意 2-不同意
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}
