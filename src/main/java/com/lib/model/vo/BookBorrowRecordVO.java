package com.lib.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author quan
 */
@Data
public class BookBorrowRecordVO implements Serializable {
    /**
     * 借书记录id
     */
    private Long id;

    /**
     * 图书vo
     */
    private BookVO bookVO;

    /**
     * 用户vo
     */
    private UserVO userVO;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 借书天数
     */
    private Integer borrowDays;
    private static final long serialVersionUID = 1L;
}
