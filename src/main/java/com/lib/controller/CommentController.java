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
import com.lib.model.dto.comments.CommentsAddRequest;
import com.lib.model.dto.comments.CommentsQueryRequest;
import com.lib.model.dto.comments.CommentsUpdateRequest;
import com.lib.model.entity.Comments;
import com.lib.model.vo.CommentsVO;
import com.lib.service.CommentsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 评论接口
 * @author quan
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentsService commentsService;

    /**
     * 分页获取评论列表
     *
     * @param commentsQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Page<Comments>> listCommentsByPage(@RequestBody CommentsQueryRequest commentsQueryRequest,
                                                   HttpServletRequest request) {
        long current = commentsQueryRequest.getCurrent();
        long size = commentsQueryRequest.getPageSize();

        Page<Comments> commentsPage = commentsService.page(new Page<>(current, size),
                commentsService.getQueryWrapper(commentsQueryRequest));

        return ResultUtils.success(commentsPage);
    }

    /**
     * 分页获取评论封装列表
     *
     * @param commentsQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<CommentsVO>> listCommentsVOByPage(@RequestBody CommentsQueryRequest commentsQueryRequest,
                                                       HttpServletRequest request) {
        if (commentsQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = commentsQueryRequest.getCurrent();
        long size = commentsQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Comments> commentsPage = commentsService.page(new Page<>(current, size),
                commentsService.getQueryWrapper(commentsQueryRequest));
        Page<CommentsVO> commentsVOPage = new Page<>(current, size, commentsPage.getTotal());
        List<CommentsVO> commentsVO = commentsService.getCommentsVO(commentsPage.getRecords(),request);
        commentsVOPage.setRecords(commentsVO);
        return ResultUtils.success(commentsVOPage);
    }

    /**
     * 添加评论
     * @param commentsAddRequest 评论添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addComments(@RequestBody CommentsAddRequest commentsAddRequest,HttpServletRequest request){
        if(commentsService.addComments(commentsAddRequest,request)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改评论
     * @param commentsUpdateRequest 评论修改请求
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Void> updateComments(@RequestBody CommentsUpdateRequest commentsUpdateRequest,HttpServletRequest request){
        if(commentsService.updateComments(commentsUpdateRequest,request)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除评论
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Void> deleteComments(@RequestBody DeleteRequest deleteRequest){
        if(commentsService.deleteComments(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
    @PostMapping("/pass")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse passComment(@RequestBody  Long id, HttpServletRequest request){
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean flag = commentsService.passComment(id,request);
        if(flag){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }
    @PostMapping("/unPass")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse unPassComment(@RequestBody  Long id, HttpServletRequest request){
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean flag = commentsService.unPassComment(id,request);
        if(flag){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }
}
