package com.lib.model.dto.announcement;

import lombok.Data;

import java.io.Serializable;

/**
 * 图书添加请求
 * @author quan
 */
@Data
public class AnnouncementAddRequest implements Serializable {
    /**
     * 公告名
     */
    private String name;

    /**
     * 公告内容
     */
    private String content;
    private static final long serialVersionUID = 1L;
}
