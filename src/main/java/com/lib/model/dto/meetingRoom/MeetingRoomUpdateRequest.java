package com.lib.model.dto.meetingRoom;

import lombok.Data;

import java.io.Serializable;

/**
 * 会议室修改请求
 * @author quan
 */
@Data
public class MeetingRoomUpdateRequest implements Serializable {
    /**
     * 图书id
     */
    private Long meetingRoomId;

    /**
     * 会议室名字
     */
    private String meetingRoomName;

    private static final long serialVersionUID = 1L;
}
