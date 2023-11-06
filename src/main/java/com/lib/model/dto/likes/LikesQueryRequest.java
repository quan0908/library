package com.lib.model.dto.likes;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞查询请求
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LikesQueryRequest extends PageRequest implements Serializable {
    /**
     * 用户账号
     */
    private String account;

    private static final long serialVersionUID = 1L;
}