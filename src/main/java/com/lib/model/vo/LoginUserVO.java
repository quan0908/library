package com.lib.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vv
 * 用户视图（脱敏）
 */
@Data
public class LoginUserVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 'BAN USER LIB_ADMIN MEETING_ROOM_ADMIN SUPPER_ADMIN'
     */
    private String role;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
