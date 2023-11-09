package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.comments.CommentsAddRequest;
import com.lib.model.dto.comments.CommentsQueryRequest;
import com.lib.model.dto.comments.CommentsUpdateRequest;
import com.lib.model.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.model.vo.CommentsVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author vv
*/
public interface CommentsService extends IService<Comments> {
    /**
     * 获取查询条件
     *
     * @param commentsQueryRequest 评论查询请求
     * @return
     */
    QueryWrapper<Comments> getQueryWrapper(CommentsQueryRequest commentsQueryRequest);

    /**
     * 获取全部评论信息
     *
     * @param commentsList 评论集合
     * @return
     */
    List<CommentsVO> getCommentsVO(List<Comments> commentsList,HttpServletRequest request);

    /**
     * 获取评论信息VO
     *
     * @param comments
     * @return
     */
    CommentsVO getCommentsVO(Comments comments, HttpServletRequest request);

    /**
     * 添加评论
     * @param commentsAddRequest 评论添加请求
     * @return 是否添加成功
     */
    boolean addComments(CommentsAddRequest commentsAddRequest, HttpServletRequest request);

    /**
     * 修改评论
     * @param commentsUpdateRequest 评论修改请求
     * @return 是否修改成功
     */
    boolean updateComments(CommentsUpdateRequest commentsUpdateRequest,HttpServletRequest request);

    /**
     * 删除评论
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteComments(DeleteRequest deleteRequest);

    boolean passComment(Long id, HttpServletRequest request);

    boolean unPassComment(Long id, HttpServletRequest request);
}
