package com.lib.model.vo;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lib.model.entity.Book;
import com.lib.model.entity.Meeting;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议VO
 */
@Data
public class MeetingVO implements Serializable {
    /**
     * 会议id
     */
    private Long id;

    /**
     * 会议名字
     */
    private String name;

    /**
     * 会议VO
     */
    private UserVO creatorVO;

    /**
     * 会议开始时间
     */
    private Date startTime;

    /**
     * 会议结束时间
     */
    private Date endTime;

    /**
     * 会议室VO
     */
    private MeetingRoomVO meetingRoomVO;

    /**
     * 包装类转对象
     *
     * @param meetingVO
     * @return
     */
    public static Meeting voToObj(MeetingVO meetingVO) {
        if (meetingVO == null) {
            return null;
        }

        Meeting meeting = new Meeting();
        BeanUtils.copyProperties(meetingVO, meeting);

        return meeting;
    }

    /**
     * 对象转包装类
     *
     * @param meeting
     * @return
     */
    public static MeetingVO objToVo(Meeting meeting) {
        if (meeting == null) {
            return null;
        }
        MeetingVO meetingVO = new MeetingVO();
        BeanUtils.copyProperties(meeting, meetingVO);
        return meetingVO;
    }
}
