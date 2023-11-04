package com.lib.model.dto.meetingRecord;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议记录添加请求
 *  @author quan
 */
@Data
public class MeetingRecordAddRequest implements Serializable {
    /**
     * 会议id
     */
    private Long meetingId;

    /**
     * 参与人id
     */
    private Long participantId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
