package com.lib.model.dto.meetingRoom;

import lombok.Data;

import java.io.Serializable;

/**
 * 会议室添加请求
 *  @author quan
 */
@Data
public class MeetingRoomAddRequest implements Serializable {
    /**
     * 会议室编号
     */
    private String name;

    /**
     * 会议室容量
     */
    private Integer capacity;

    private static final long serialVersionUID = 1L;
}
