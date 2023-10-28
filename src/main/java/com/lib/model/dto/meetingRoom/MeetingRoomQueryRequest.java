package com.lib.model.dto.meetingRoom;



import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 会议室查询请求
 *
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingRoomQueryRequest extends PageRequest implements Serializable {
    /**
     * 会议室名字
     */
    private String meetingRoomName;

    private static final long serialVersionUID = 1L;
}