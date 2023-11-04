package com.lib.model.vo;

import com.lib.model.entity.MeetingRoom;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author quan
 */
@Data
public class MeetingRoomVO implements Serializable {
    /**
     * 会议室id
     */
    private Long id;

    /**
     * 会议室名字
     */
    private String name;

    /**
     * 会议室容量
     */
    private Integer capacity;

    /**
     * 会议室是否空
     */
    private Integer isEmpty;

    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param meetingRoomVO
     * @return
     */
    public static MeetingRoom voToObj(MeetingRoomVO meetingRoomVO) {
        if (meetingRoomVO == null) {
            return null;
        }
        MeetingRoom meetingRoom = new MeetingRoom();
        BeanUtils.copyProperties(meetingRoomVO, meetingRoom);

        return meetingRoom;
    }

    /**
     * 对象转包装类
     *
     * @param meetingRoom
     * @return
     */
    public static MeetingRoomVO objToVo(MeetingRoom meetingRoom) {
        if (meetingRoom == null) {
            return null;
        }
        MeetingRoomVO meetingRoomVO = new MeetingRoomVO();
        BeanUtils.copyProperties(meetingRoom, meetingRoomVO);
        return meetingRoomVO;
    }
}
