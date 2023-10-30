package com.lib.model.dto.blacklist;



import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 黑名单查询请求
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlacklistQueryRequest extends PageRequest implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}