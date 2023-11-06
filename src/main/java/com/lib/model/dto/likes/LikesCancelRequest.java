package com.lib.model.dto.likes;

import lombok.Data;

import java.io.Serializable;

/**
 * 点赞取消请求
 *  @author quan
 */
@Data
public class LikesCancelRequest implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;
    private static final long serialVersionUID = 1L;
}
