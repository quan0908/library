package com.lib.model.dto.meetingRecord;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议记录修改请求
 *  @author quan
 */
@Data
public class MeetingRecordUpdateRequest implements Serializable {
    /**
     * 会议记录id
     */
    private Long id;

    /**
     * 会议室id
     */
    private Long meetingRoomId;

    /**
     * 0-未审核 1-同意 2-不同意
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}
