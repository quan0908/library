package com.lib.model.dto.bookBorrowRecord;

import lombok.Data;

import java.io.Serializable;

/**
 * 图书记录修改请求
 *  @author quan
 */
@Data
public class BookBorrowRecordUpdateRequest implements Serializable {
    /**
     * 借书记录id
     */
    private Long bookBorrowRecordId;

    private static final long serialVersionUID = 1L;
}
