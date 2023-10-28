package com.lib.model.dto.book;

import lombok.Data;

import java.io.Serializable;

/**
 * 图书添加请求
 */
@Data
public class BookAddRequest implements Serializable {
    /**
     * 图书名字
     */
    private String bookName;

    /**
     * 图书数量
     */
    private Integer bookNumber;

    /**
     * 图书类型
     */
    private String type;

    /**
     * 图书位置
     */
    private String bookLocation;
    private static final long serialVersionUID = 1L;
}
