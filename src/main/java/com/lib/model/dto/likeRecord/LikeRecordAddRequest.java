package com.lib.model.dto.likeRecord;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞记录添加请求
 *  @author quan
 */
@Data
public class LikeRecordAddRequest implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;
    private static final long serialVersionUID = 1L;
}
