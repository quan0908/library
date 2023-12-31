package com.lib.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lib.common.BaseResponse;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.common.ResultUtils;
import com.lib.exception.BusinessException;
import com.lib.exception.ThrowUtils;
import com.lib.model.dto.likeRecord.LikeRecordAddRequest;
import com.lib.model.dto.likeRecord.LikeRecordCancelRequest;
import com.lib.model.dto.likeRecord.LikeRecordQueryRequest;
import com.lib.model.entity.LikeRecord;
import com.lib.model.entity.User;
import com.lib.model.vo.LikeRecordVO;
import com.lib.service.LikeRecordService;
import com.lib.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/likeRecord")
public class LikeRecordController {
    @Resource
    private LikeRecordService likeRecordService;

    @Resource
    private UserService userService;

    /**
     * 分页获取点赞记录列表
     *
     * @param likeRecordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page") 
    public BaseResponse<Page<LikeRecord>> listLikeRecordByPage(@RequestBody LikeRecordQueryRequest likeRecordQueryRequest,
                                                   HttpServletRequest request) {
        long current = likeRecordQueryRequest.getCurrent();
        long size = likeRecordQueryRequest.getPageSize();

        Page<LikeRecord> likeRecordPage = likeRecordService.page(new Page<>(current, size),
                likeRecordService.getQueryWrapper(likeRecordQueryRequest));

        return ResultUtils.success(likeRecordPage);
    }

    /**
     * 分页获取点赞记录封装列表
     * @param likeRecordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<LikeRecordVO>> listLikeRecordVOByPage(@RequestBody LikeRecordQueryRequest likeRecordQueryRequest,
                                                       HttpServletRequest request) {
        if (likeRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = likeRecordQueryRequest.getCurrent();
        long size = likeRecordQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<LikeRecord> likeRecordPage = likeRecordService.page(new Page<>(current, size),
                likeRecordService.getQueryWrapper(likeRecordQueryRequest));
        Page<LikeRecordVO> likeRecordVOPage = new Page<>(current, size, likeRecordPage.getTotal());
        List<LikeRecordVO> likeRecordVO = likeRecordService.getLikeRecordVO(likeRecordPage.getRecords(),request);
        likeRecordVOPage.setRecords(likeRecordVO);
        return ResultUtils.success(likeRecordVOPage);
    }

    /**
     * 添加点赞记录
     * @param likeRecordAddRequest 点赞记录添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addLikeRecord(@RequestBody LikeRecordAddRequest likeRecordAddRequest,
                                               HttpServletRequest request){
        if(likeRecordService.addLikeRecord(likeRecordAddRequest,request)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }


    /**
     * 点赞 / 取消点赞
     *
     * @param likeRecordAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/like")
    public BaseResponse<Integer> doLike(@RequestBody LikeRecordAddRequest likeRecordAddRequest,
                                         HttpServletRequest request) {
        if (likeRecordAddRequest == null || likeRecordAddRequest.getCommentId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long commentId = likeRecordAddRequest.getCommentId();
        int result = likeRecordService.doCommentLike(commentId, loginUser);
        return ResultUtils.success(result);
    }
}
