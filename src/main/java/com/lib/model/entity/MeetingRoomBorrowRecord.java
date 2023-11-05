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
     * 会议室记录id
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
     * 租借时间
     */
    private Date borrowTime;

    /**
     * 是否归还
     */
    private Integer isReturn;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}