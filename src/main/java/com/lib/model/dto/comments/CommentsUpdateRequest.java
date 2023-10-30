package com.lib.model.dto.comments;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论修改请求
 *  @author quan
 */
@Data
public class CommentsUpdateRequest implements Serializable {
    /**
     * 评论id
     */
    private Long id;

    /**
     * 0-待审核 1-审核通过 2-审核未通过
     */
    private Integer isChecked;

    private static final long serialVersionUID = 1L;
}
