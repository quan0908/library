package com.lib.model.dto.comments;



import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 评论查询请求
 *  @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentsQueryRequest extends PageRequest implements Serializable {
    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户id
     */
    private Long userId;

    /**
     * 评论图书id
     */
    private Long bookId;

    /**
     * 0-待审核 1-审核通过 2-审核未通过
     */
    private Integer isChecked;

    private static final long serialVersionUID = 1L;
}