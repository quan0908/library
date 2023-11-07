package com.lib.model.dto.likeRecord;

import lombok.Data;

import java.io.Serializable;

/**
 * 取消点赞请求
 */
@Data
public class LikeRecordCancelRequest implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;
    private static final long serialVersionUID = 1L;
}
