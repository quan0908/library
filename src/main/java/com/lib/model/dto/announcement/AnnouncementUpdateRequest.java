package com.lib.model.dto.announcement;

import lombok.Data;

import java.io.Serializable;

/**
 * 图书修改请求
 * @author quan
 */
@Data
public class AnnouncementUpdateRequest implements Serializable {
    /**
     * 公告名
     */
    private String name;

    /**
     * 公共id
     */
    private Long id;

    /**
     * 公告内容
     */
    private String content;
    private static final long serialVersionUID = 1L;
}
