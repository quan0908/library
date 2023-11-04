package com.lib.model.dto.meeting;
import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 会议查询请求
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingQueryRequest extends PageRequest implements Serializable {
    /**
     * 会议id
     */
    private Long id;

    /**
     * 会议名字
     */
    private String name;

    /**
     * 会议发起人账号
     */
    private String account;

    /**
     * 会议室ID
     */
    private Long meetingRoomId;
    private static final long serialVersionUID = 1L;
}