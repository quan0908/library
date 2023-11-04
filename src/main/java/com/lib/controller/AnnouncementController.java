package com.lib.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lib.annotation.AuthCheck;
import com.lib.common.BaseResponse;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.common.ResultUtils;
import com.lib.constant.UserConstant;
import com.lib.exception.BusinessException;
import com.lib.exception.ThrowUtils;
import com.lib.model.dto.announcement.*;
import com.lib.model.entity.Announcement;
import com.lib.model.vo.AnnouncementVO;
import com.lib.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 公告接口
 * @author quan
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Resource
    private AnnouncementService announcementService;
    
    /**
     * 分页获取公告列表
     *
     * @param announcementQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Page<Announcement>> listAnnouncementByPage(@RequestBody AnnouncementQueryRequest announcementQueryRequest,
                                                   HttpServletRequest request) {
        long current = announcementQueryRequest.getCurrent();
        long size = announcementQueryRequest.getPageSize();

        Page<Announcement> announcementPage = announcementService.page(new Page<>(current, size),
                announcementService.getQueryWrapper(announcementQueryRequest));

        return ResultUtils.success(announcementPage);
    }

    /**
     * 分页获取公告封装列表
     * @param announcementQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<AnnouncementVO>> listAnnouncementVOByPage(@RequestBody AnnouncementQueryRequest announcementQueryRequest,
                                                       HttpServletRequest request) {
        if (announcementQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = announcementQueryRequest.getCurrent();
        long size = announcementQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Announcement> announcementPage = announcementService.page(new Page<>(current, size),
                announcementService.getQueryWrapper(announcementQueryRequest));
        Page<AnnouncementVO> announcementVOPage = new Page<>(current, size, announcementPage.getTotal());
        List<AnnouncementVO> announcementVO = announcementService.getAnnouncementVO(announcementPage.getRecords());
        announcementVOPage.setRecords(announcementVO);
        return ResultUtils.success(announcementVOPage);
    }

    /**
     * 添加公告
     * @param announcementAddRequest 公告添加请求
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Boolean> addAnnouncement(@RequestBody AnnouncementAddRequest announcementAddRequest,
                                                 HttpServletRequest request){
        if(announcementService.addAnnouncement(announcementAddRequest,request)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改公告
     * @param announcementUpdateRequest 公告修改请求
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Void> updateAnnouncement(@RequestBody AnnouncementUpdateRequest announcementUpdateRequest){
        if(announcementService.updateAnnouncement(announcementUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除公告
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Void> deleteAnnouncement(@RequestBody DeleteRequest deleteRequest){
        if(announcementService.deleteAnnouncement(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }

    @GetMapping("/get")
    public BaseResponse<AnnouncementVO> getAnnouncementById(Long id){
        if(id == null || id <= 0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Announcement announcement = announcementService.getById(id);
        if(announcement == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        AnnouncementVO announcementVO = announcementService.getAnnouncementVO(announcement);

        return ResultUtils.success(announcementVO);
    }
}
