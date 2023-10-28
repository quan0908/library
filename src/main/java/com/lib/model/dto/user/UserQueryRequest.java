package com.lib.model.dto.user;



import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @author vv
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;


    /**
     * 'BAN USER LIB_ADMIN MEETING_ROOM_ADMIN SUPPER_ADMIN'
     */
    private String role;

    private String idCard;

    private static final long serialVersionUID = 1L;
}