package com.lib.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 借书记录VO
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
    private Date createTime;

    /**
     * 0-未归还 1-已归还
     */
    private Integer isReturned;

    /**
     * 借书天数
     */
    private Integer borrowDays;
    private static final long serialVersionUID = 1L;
}
