package com.lib.model.dto.appeal;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vv
 */
@Data
public class AppealUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户解封理由
     */
    private String userReason;

    /**
     * 0 - 图书管理审核 1-系统管理员审核
     */
    private Integer status;

}
