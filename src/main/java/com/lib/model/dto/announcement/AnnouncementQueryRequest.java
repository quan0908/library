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
     * 公告名
     */
    private String name;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告发布人账号
     */
    private String account;

    private static final long serialVersionUID = 1L;
}