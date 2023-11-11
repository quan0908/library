package com.lib.model.dto.appeal;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vv
 */
@Data
public class AppealAddRequest implements Serializable {
    /**
     * 申请用户
     */
    private Long userId;

    /**
     * 身份
     */
    private String idCard;

    private String username;

    private String password;

    private String account;

    /**
     * 用户解封理由
     */
    private String userReason;

}
