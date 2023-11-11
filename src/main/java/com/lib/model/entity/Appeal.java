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
 * @TableName appeal
 */
@TableName(value ="appeal")
@Data
public class Appeal implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 申请用户
     */
    private Long userId;

    /**
     * 用户解封理由
     */
    private String userReason;

    /**
     * 0 - 图书管理审核 1-系统管理员审核
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

    /**
     * 0-不删 1删
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}