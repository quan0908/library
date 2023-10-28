package com.lib.model.dto.meetingRoomBorrow;
import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议室记录查询请求
 *
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingRoomBorrowRecordQueryRequest extends PageRequest implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 会议室id
     */
    private Long meetingRoomId;

    /**
     * 借用时间
     */
    private Date borrowTime;

    private static final long serialVersionUID = 1L;
}