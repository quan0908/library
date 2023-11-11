package com.lib.model.dto.appeal;

import com.lib.common.PageRequest;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author vv
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppealQueryRequest extends PageRequest implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    private Integer status;
}
