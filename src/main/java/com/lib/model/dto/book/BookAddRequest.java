package com.lib.model.dto.book;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 图书添加请求
 */
@Data
public class BookAddRequest implements Serializable {
    /**
     * 图书名
     */
    private String bookName;

    /**
     * 图书数量
     */
    private Integer bookNumber;

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

    private static final long serialVersionUID = 1L;
}
