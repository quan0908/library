package com.lib.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lib.common.BaseResponse;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.common.ResultUtils;
import com.lib.exception.BusinessException;
import com.lib.exception.ThrowUtils;
import com.lib.model.dto.likes.LikesAddRequest;
import com.lib.model.dto.likes.LikesCancelRequest;
import com.lib.model.dto.likes.LikesQueryRequest;
import com.lib.model.entity.Likes;
import com.lib.model.vo.LikesVO;
import com.lib.service.LikeRecordService;
import com.lib.service.LikesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikesController {
    @Resource
    private LikesService likesService;

    @Resource
    private LikeRecordService likeRecordService;
    
    /**
     * 分页获取点赞列表
     *
     * @param likesQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page") 
    public BaseResponse<Page<Likes>> listLikesByPage(@RequestBody LikesQueryRequest likesQueryRequest,
                                                   HttpServletRequest request) {
        long current = likesQueryRequest.getCurrent();
        long size = likesQueryRequest.getPageSize();

        Page<Likes> likesPage = likesService.page(new Page<>(current, size),
                likesService.getQueryWrapper(likesQueryRequest));

        return ResultUtils.success(likesPage);
    }

    /**
     * 分页获取点赞封装列表
     * @param likesQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<LikesVO>> listLikesVOByPage(@RequestBody LikesQueryRequest likesQueryRequest,
                                                       HttpServletRequest request) {
        if (likesQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = likesQueryRequest.getCurrent();
        long size = likesQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Likes> likesPage = likesService.page(new Page<>(current, size),
                likesService.getQueryWrapper(likesQueryRequest));
        Page<LikesVO> likesVOPage = new Page<>(current, size, likesPage.getTotal());
        List<LikesVO> likesVO = likesService.getLikesVO(likesPage.getRecords());
        likesVOPage.setRecords(likesVO);
        return ResultUtils.success(likesVOPage);
    }

    /**
     * 添加点赞
     * @param likesAddRequest 点赞添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addLikes(@RequestBody LikesAddRequest likesAddRequest,
                                                        HttpServletRequest request){
        if(likesService.addLikes(likesAddRequest,request)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 删除点赞
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete") 
    public BaseResponse<Void> deleteLikes(@RequestBody DeleteRequest deleteRequest){
        if(likesService.deleteLikes(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }

    /**
     * 点赞
     * @param likesAddRequest
     * @param request
     * @return
     */
    @PostMapping("/like")
    public BaseResponse<Void> like(@RequestBody LikesAddRequest likesAddRequest,
                                                HttpServletRequest request){
        if(!likeRecordService.like(likesAddRequest,request)){
            return ResultUtils.error(ErrorCode.OPERATION_ERROR,"点赞失败");
        }
        return ResultUtils.success(null);
    }

    /**
     * 取消点赞
     * @return
     */
    @PostMapping("/cancel")
    public BaseResponse<Void> cancelLike(@RequestBody LikesCancelRequest likesCancelRequest,
                                   HttpServletRequest request){
        if(!likeRecordService.cancelLike(likesCancelRequest,request)){
            return ResultUtils.error(ErrorCode.OPERATION_ERROR,"取消点赞失败");
        }
        return ResultUtils.success(null);
    }
}
