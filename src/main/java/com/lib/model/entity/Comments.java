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
 * @TableName comments
 */
@TableName(value ="comments")
@Data
public class Comments implements Serializable {
    /**
     * 评论id
     */
    @TableId
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户id
     */
    private Long userId;

    /**
     * 评论图书id
     */
    private Long bookId;

    /**
     * 审核人id
     */
    private Long checkUserId;

    /**
     * 0-待审核 1-审核通过 2-审核未通过
     */
    private Integer isChecked;

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