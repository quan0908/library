package com.lib.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName meeting_record
 */
@TableName(value ="meeting_record")
@Data
public class MeetingRecord implements Serializable {
    /**
     * 会议记录id
     */
    @TableId
    private Long id;

    /**
     * 会议id
     */
    private Long meetingId;

    /**
     * 参与人id
     */
    private Long participantId;

    /**
     * 0-未审核 1-同意 2-不同意
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableLogic
    /**
     * 0-不删 1删
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}