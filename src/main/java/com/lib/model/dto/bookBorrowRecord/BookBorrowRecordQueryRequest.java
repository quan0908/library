package com.lib.model.dto.bookBorrowRecord;



import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 图书记录查询请求
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BookBorrowRecordQueryRequest extends PageRequest implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 图书id
     */
    private Long bookId;

    private static final long serialVersionUID = 1L;
}