package com.lib.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName meeting_room_borrow_record
 */
@TableName(value ="meeting_room_borrow_record")
@Data
public class MeetingRoomBorrowRecord implements Serializable {
    /**
     * 会议室id
     */
    @TableId
    private Long id;

    /**
     * 租借用户id
     */
    private Long userId;

    /**
     * 租借会议室id
     */
    private Long meetingRoomId;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0-不删 1删
     */
    private Integer isDelete;

    /**
     * 审核人id
     */
    private String checkUserId;

    /**
     * 0-未审核 1-同意 2-不同意
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}