package com.lib.model.dto.announcement;



import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 图书查询请求
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnnouncementQueryRequest extends PageRequest implements Serializable {
    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告发布人
     */
    private Long creatorId;

    private static final long serialVersionUID = 1L;
}