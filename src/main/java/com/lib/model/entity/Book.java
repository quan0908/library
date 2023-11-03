package com.lib.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName book
 */
@TableName(value ="book")
@Data
public class Book implements Serializable {
    /**
     * 图书id
     */
    @TableId
    private Long id;

    /**
     * 图书名
     */
    private String bookName;

    /**
     * 图书数量
     */
    private Integer bookNumber;

    /**
     * 图书可借数量
     */
    private Integer bookRemaining;

    /**
     * 图书分类
     */
    private String type;

    /**
     * 图书位置
     */
    private String bookLocation;

    /**
     * 图书作者
     */
    private String bookAuthor;

    /**
     * 图书简介
     */
    private String bookTra;

    /**
     * 图书封面
     */
    private String bookCover;

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