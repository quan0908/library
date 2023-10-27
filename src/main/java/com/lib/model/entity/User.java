package com.lib.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户表id
     */
    @TableId
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String username;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 密码
     */
    private String password;

    /**
     *
     */
    private String userAvatar;

    /**
     * 违规次数
     */
    private Integer foulTimes;

    /**
     * 'BAN USER LIB_ADMIN MEETING_ROOM_ADMIN SUPPER_ADMIN'
     */
    private String role;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0-不删 1删
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}