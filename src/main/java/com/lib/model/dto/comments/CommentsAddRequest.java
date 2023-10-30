package com.lib.model.dto.comments;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 评论添加请求
 *  @author quan
 */
@Data
public class CommentsAddRequest implements Serializable {
    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论图书id
     */
    private Long bookId;

    private static final long serialVersionUID = 1L;
}
