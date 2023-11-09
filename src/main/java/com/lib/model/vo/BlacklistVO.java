package com.lib.model.vo;

import com.lib.model.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * 黑名单VO
 */
@Data
public class BlacklistVO implements Serializable {
    /**
     * 黑名单id
     */
    private Long id;

    /**
     * 用户
     */
    private User blackUser;

    private static final long serialVersionUID = 1L;
}
