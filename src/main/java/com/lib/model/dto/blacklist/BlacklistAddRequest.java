package com.lib.model.dto.blacklist;

import lombok.Data;

import java.io.Serializable;

/**
 * 黑名单添加请求
 * @author quan
 */
@Data
public class BlacklistAddRequest implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
