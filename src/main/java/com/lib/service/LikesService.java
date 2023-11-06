package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.likes.LikesAddRequest;
import com.lib.model.dto.likes.LikesQueryRequest;
import com.lib.model.entity.Likes;
import com.lib.model.vo.LikesVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author quan
*/
public interface LikesService extends IService<Likes> {
    /**
     * 获取查询条件
     *
     * @param likesQueryRequest 点赞查询请求
     * @return
     */
    QueryWrapper<Likes> getQueryWrapper(LikesQueryRequest likesQueryRequest);

    /**
     * 获取全部点赞信息
     *
     * @param likesList 点赞集合
     * @return
     */
    List<LikesVO> getLikesVO(List<Likes> likesList);

    /**
     * 获取点赞信息VO
     *
     * @param likes
     * @return
     */
    LikesVO getLikesVO(Likes likes);

    /**
     * 添加点赞
     * @param likesAddRequest 点赞添加请求
     * @return 是否添加成功
     */
    boolean addLikes(LikesAddRequest likesAddRequest, HttpServletRequest request);

//    boolean updateLikes(LikesUpdateRequest likesUpdateRequest);

    /**
     * 删除图书
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteLikes(DeleteRequest deleteRequest);
}
