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
     * 会议室id
     */
    private Long id;

    /**
     * 会议室编号
     */
    private String name;;

    /**
     * 会议室容量
     */
    private Integer capacity;


    private Integer isEmpty;

    private static final long serialVersionUID = 1L;
}
