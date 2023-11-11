package com.lib.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vv
 */
@Data
public class AppealVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 申请用户
     */
    private UserVO appealUser;

    /**
     * 用户解封理由
     */
    private String userReason;

    /**
     * 0 - 图书管理审核 1-系统管理员审核
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

}
