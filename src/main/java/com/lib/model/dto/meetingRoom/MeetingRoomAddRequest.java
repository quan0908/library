package com.lib.model.dto.meetingRoom;

import lombok.Data;

import java.io.Serializable;

/**
 * 会议室添加请求
 */
@Data
public class MeetingRoomAddRequest implements Serializable {
    /**
     * 会议室名字
     */
    private String meetingRoomName;

    private static final long serialVersionUID = 1L;
}
