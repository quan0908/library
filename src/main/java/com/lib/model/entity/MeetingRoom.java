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
 * @TableName meeting_room
 */
@TableName(value ="meeting_room")
@Data
public class MeetingRoom implements Serializable {
    /**
     * 会议室id
     */
    @TableId
    private Long id;

    /**
     * 会议室编号
     */
    private String name;

    /**
     * 会议室容量
     */
    private Integer capacity;

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