package com.lib.model.dto.blacklist;

import lombok.Data;

import java.io.Serializable;

/**
 * @author vv
 */
@Data
public class BlacklistMoveOutRequest implements Serializable {
    private Long id;
    private Long userId;
}
