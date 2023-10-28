package com.lib.model.dto.book;



import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 图书查询请求
 *
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BookQueryRequest extends PageRequest implements Serializable {
    /**
     * 图书名字
     */
    private String bookName;

    /**
     * 图书类型
     */
    private String type;

    private static final long serialVersionUID = 1L;
}