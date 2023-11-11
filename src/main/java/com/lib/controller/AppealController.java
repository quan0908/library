package com.lib.controller;

/**
 * @author vv
 */

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lib.annotation.AuthCheck;
import com.lib.common.BaseResponse;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.common.ResultUtils;
import com.lib.constant.UserConstant;
import com.lib.exception.BusinessException;
import com.lib.exception.ThrowUtils;
import com.lib.model.dto.appeal.AppealQueryRequest;
import com.lib.model.dto.appeal.AppealUpdateRequest;
import com.lib.model.dto.appeal.AppealAddRequest;
import com.lib.model.entity.Appeal;
import com.lib.model.entity.Appeal;
import com.lib.model.vo.AppealVO;
import com.lib.service.AppealService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/appeal")
public class AppealController {

    @Resource
    private AppealService appealService;

    @PostMapping("/add")
    public BaseResponse addAppeal(@RequestBody AppealAddRequest appealAddRequest){
        if(appealAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        boolean flag = appealService.addAppeal(appealAddRequest);
        if(!flag){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(null);
    }

    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Page<Appeal>> listAppealByPage(@RequestBody AppealQueryRequest appealQueryRequest,
                                                                   HttpServletRequest request) {
        long current = appealQueryRequest.getCurrent();
        long size = appealQueryRequest.getPageSize();

        Page<Appeal> appealPage = appealService.page(new Page<>(current, size),
                appealService.getQueryWrapper(appealQueryRequest));

        return ResultUtils.success(appealPage);
    }


    @PostMapping("/list/page/vo")
    public BaseResponse<Page<AppealVO>> listAppealVOByPage(@RequestBody AppealQueryRequest appealQueryRequest,
                                                                       HttpServletRequest request) {
        if (appealQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = appealQueryRequest.getCurrent();
        long size = appealQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Appeal> appealPage = appealService.page(new Page<>(current, size),
                appealService.getQueryWrapper(appealQueryRequest));
        Page<AppealVO> appealVOPage = new Page<>(current, size, appealPage.getTotal());
        List<AppealVO> appealVO = appealService.getAppealVO(appealPage.getRecords());
        appealVOPage.setRecords(appealVO);
        return ResultUtils.success(appealVOPage);
    }

    @PostMapping("/update")
    public BaseResponse<Void> updateAppeal(@RequestBody AppealUpdateRequest appealUpdateRequest){
        if(appealService.updateAppeal(appealUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }


    @PostMapping("/delete")
    public BaseResponse<Void> deleteAppeal(@RequestBody DeleteRequest deleteRequest){
        if(appealService.deleteAppeal(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
}
