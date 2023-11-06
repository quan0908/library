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
     * 点赞id
     */
    private Long likesId;

    /**
     * 点赞状态 0-点赞 1-取消点赞
     */
    private Integer status;
    private static final long serialVersionUID = 1L;
}
