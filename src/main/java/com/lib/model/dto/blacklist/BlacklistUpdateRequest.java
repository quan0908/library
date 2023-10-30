package com.lib.model.dto.blacklist;

import lombok.Data;

import java.io.Serializable;

/**
 * 黑名单修改请求
 * @author quan
 */
@Data
public class BlacklistUpdateRequest implements Serializable {
    /**
     * 黑名单id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
