package com.lib.model.dto.book;

import lombok.Data;

import java.io.Serializable;

/**
 * 还书请求
 */
@Data
public class BookReturnRequest implements Serializable {
    /**
     * 借书记录id
     */
    private Long borrowBookRecordId;
    private static final long serialVersionUID = 1L;
}