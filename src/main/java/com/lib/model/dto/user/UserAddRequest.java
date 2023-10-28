package com.lib.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author vv
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String account;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     *角色
     */
    private String role;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = 1L;
}