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
     * 用户头像
     */
    private String userAvatar;

    /**
     *
     */
    private String role;

    private static final long serialVersionUID = 1L;
}