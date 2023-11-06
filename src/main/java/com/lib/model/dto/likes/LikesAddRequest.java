package com.lib.model.dto.likes;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞添加请求
 *  @author quan
 */
@Data
public class LikesAddRequest implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;
    private static final long serialVersionUID = 1L;
}
