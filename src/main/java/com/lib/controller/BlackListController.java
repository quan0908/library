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
import com.lib.model.dto.blacklist.BlacklistAddRequest;
import com.lib.model.dto.blacklist.BlacklistQueryRequest;
import com.lib.model.dto.blacklist.BlacklistUpdateRequest;
import com.lib.model.entity.Blacklist;
import com.lib.model.vo.BlacklistVO;
import com.lib.service.BlacklistService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 黑名单接口
 */
@RestController
@RequestMapping("/blacklist")
public class BlackListController {
    @Resource
    private BlacklistService blacklistService;

    /**
     * 分页获取图黑名单列表
     *
     * @param blacklistQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Blacklist>> listBlacklistByPage(@RequestBody BlacklistQueryRequest blacklistQueryRequest,
                                                   HttpServletRequest request) {
        long current = blacklistQueryRequest.getCurrent();
        long size = blacklistQueryRequest.getPageSize();

        Page<Blacklist> blacklistPage = blacklistService.page(new Page<>(current, size),
                blacklistService.getQueryWrapper(blacklistQueryRequest));

        return ResultUtils.success(blacklistPage);
    }

    /**
     * 分页获取黑名单封装列表
     *
     * @param blacklistQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<BlacklistVO>> listBlacklistVOByPage(@RequestBody BlacklistQueryRequest blacklistQueryRequest,
                                                       HttpServletRequest request) {
        if (blacklistQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = blacklistQueryRequest.getCurrent();
        long size = blacklistQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Blacklist> blacklistPage = blacklistService.page(new Page<>(current, size),
                blacklistService.getQueryWrapper(blacklistQueryRequest));
        Page<BlacklistVO> blacklistVOPage = new Page<>(current, size, blacklistPage.getTotal());
        List<BlacklistVO> blacklistVO = blacklistService.getBlacklistVO(blacklistPage.getRecords());
        blacklistVOPage.setRecords(blacklistVO);
        return ResultUtils.success(blacklistVOPage);
    }

    /**
     * 添加图黑名单
     * @param blacklistAddRequest 图黑名单添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addBlacklist(@RequestBody BlacklistAddRequest blacklistAddRequest){
        if(blacklistService.addBlacklist(blacklistAddRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改图黑名单
     * @param blacklistUpdateRequest 图黑名单修改请求
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Void> updateBlacklist(@RequestBody BlacklistUpdateRequest blacklistUpdateRequest){
        if(blacklistService.updateBlacklist(blacklistUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除图黑名单
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Void> deleteBlacklist(@RequestBody DeleteRequest deleteRequest){
        if(blacklistService.deleteBlacklist(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
}
