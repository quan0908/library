package com.lib.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author vv
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    private String idCard;

    private String account;

    /**
     * 用户头像
     */
    private String userAvatar;


    /**
     * 'BAN USER LIB_ADMIN MEETING_ROOM_ADMIN SUPPER_ADMIN'
     */
    private String role;


    private static final long serialVersionUID = 1L;
}