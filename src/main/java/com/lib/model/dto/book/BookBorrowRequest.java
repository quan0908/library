package com.lib.model.dto.book;

import lombok.Data;

import java.io.Serializable;

/**
 * 借书请求
 */
@Data
public class BookBorrowRequest implements Serializable {
    /**
     * 图书id
     */
    private Long bookId;

    /**
     * 借书天数
     */
    private Integer borrowDays;
    private static final long serialVersionUID = 1L;
}
