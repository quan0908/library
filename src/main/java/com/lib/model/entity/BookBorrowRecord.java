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
 * @TableName book_borrow_record
 */
@TableName(value ="book_borrow_record")
@Data
public class BookBorrowRecord implements Serializable {
    /**
     * 图书借阅记录表
     */
    @TableId
    private Long id;

    /**
     * 借阅用户id
     */
    private Long userId;

    /**
     * 借阅图书id
     */
    private Long bookId;

    /**
     * 借阅天数
     */
    private Integer borrowDays;

    /**
     * 0-未归还 1-已归还
     */
    private Integer isReturned;

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