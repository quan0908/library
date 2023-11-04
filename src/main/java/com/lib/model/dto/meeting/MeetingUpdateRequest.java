package com.lib.model.dto.meeting;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议修改请求
 * @author quan
 */
@Data
public class MeetingUpdateRequest implements Serializable {
    /**
     * 会议名字
     */
    private String name;

    /**
     * 会议开始时间
     */
    private Date startTime;

    /**
     * 会议结束时间
     */
    private Date endTime;

    /**
     * 会议室ID
     */
    private Long meetingRoomId;
    private static final long serialVersionUID = 1L;
}
