package com.lib.constant;

/**
 * 用户常量
 *
 * @author vv
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 超级管理员角色
     */
    String SUPER_ADMIN = "superAdmin";

    /**
     * 图书管理员
     */
    String BOOK_ADMIN = "bookAdmin";

    /**
     * 会议室管理员
     */
    String MEETING_ROOM_ADMIN = "meetingRoomAdmin";
    /**
     * 被封号
     */
    String BAN_ROLE = "ban";


    // endregion
}
