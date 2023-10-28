package com.lib.model.dto.bookBorrowRecord;

import lombok.Data;

import java.io.Serializable;

/**
 * 图书记录添加请求
 */
@Data
public class BookBorrowRecordAddRequest implements Serializable {
    /**
     * 图书ID
     */
    private Long bookId;

    /**
     * 借书天数
     */
    private Integer borrowDays;

    private static final long serialVersionUID = 1L;
}
