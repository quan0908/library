package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.announcement.AnnouncementAddRequest;
import com.lib.model.dto.announcement.AnnouncementQueryRequest;
import com.lib.model.dto.announcement.AnnouncementUpdateRequest;
import com.lib.model.entity.Announcement;
import com.lib.model.entity.Appeal;
import com.lib.model.entity.Appeal;
import com.lib.model.vo.AnnouncementVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author quan
*/
public interface AnnouncementService extends IService<Announcement> {
    /**
     * 获取查询条件
     *
     * @param announcementQueryRequest 公告查询请求
     * @return
     */
    QueryWrapper<Announcement> getQueryWrapper(AnnouncementQueryRequest announcementQueryRequest);

    /**
     * 获取全部公告信息
     *
     * @param announcementList 公告集合
     * @return
     */
    List<AnnouncementVO> getAnnouncementVO(List<Announcement> announcementList);

    /**
     * 获取公告信息VO
     *
     * @param announcement
     * @return
     */
    AnnouncementVO getAnnouncementVO(Announcement announcement);

    /**
     * 添加公告
     * @param announcementAddRequest 公告添加请求
     * @return 是否添加成功
     */
    boolean addAnnouncement(AnnouncementAddRequest announcementAddRequest, HttpServletRequest request);

    /**
     * 修改公告
     * @param announcementUpdateRequest 公告修改请求
     * @return 是否修改成功
     */
    boolean updateAnnouncement(AnnouncementUpdateRequest announcementUpdateRequest);

    /**
     * 删除公告
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteAnnouncement(DeleteRequest deleteRequest);

    /**
     * 获取最新公告
     */
    AnnouncementVO getNewAnnouncement();
}
