package com.lib.model.dto.meeting;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议修改请求
 * @author quan
 */
@Data
public class MeetingUpdateRequest implements Serializable {
    /**
     * 会议id
     */
    private Long id;

    /**
     * 会议名字
     */
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    /**
     * 会议开始时间
     */
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
