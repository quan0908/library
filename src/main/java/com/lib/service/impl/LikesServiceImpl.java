package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.mapper.LikesMapper;

import com.lib.model.dto.likes.LikesAddRequest;
import com.lib.model.dto.likes.LikesQueryRequest;
import com.lib.model.entity.Comments;
import com.lib.model.entity.Likes;

import com.lib.model.entity.User;
import com.lib.model.vo.CommentsVO;
import com.lib.model.vo.LikesVO;
import com.lib.model.vo.UserVO;
import com.lib.service.CommentsService;

import com.lib.service.LikesService;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author am
* @description 针对表【likess】的数据库操作Service实现
* @createDate 2023-11-06 14:12:04
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService {
    @Resource
    private UserService userService;

    @Resource
    private CommentsService commentsService;

    /**
     * 获取点赞查询条件(根据点赞用户账号)
     * @param likesQueryRequest 点赞查询请求
     * @return
     */
    @Override
    public QueryWrapper<Likes> getQueryWrapper(LikesQueryRequest likesQueryRequest) {
        if (likesQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        String account = likesQueryRequest.getAccount();
        User user = new User();
        Long userId = user.getId();
        if(StringUtils.isNotBlank(account)){
            //根据用户账号查找userId
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account",account);
            user = userService.getOne(queryWrapper);
            if(user == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            userId = user.getId();
        }

        String sortField = likesQueryRequest.getSortField();
        String sortOrder = likesQueryRequest.getSortOrder();

        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(account),"userId",userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<LikesVO> getLikesVO(List<Likes> likesList) {
        if (CollectionUtils.isEmpty(likesList)) {
            return new ArrayList<>();
        }
        return likesList.stream().map(this::getLikesVO).collect(Collectors.toList());
    }

    @Override
    public LikesVO getLikesVO(Likes likes) {
        if (likes == null) {
            return null;
        }

        Long commentId = likes.getCommentId();
        Long userId = likes.getUserId();
        if(commentId == null || userId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //转成userVO
        User user = userService.getById(userId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        //转成likesRoomVO
        Comments comments = commentsService.getById(commentId);
        if(comments == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CommentsVO commentsVO = CommentsVO.objToVo(comments);

        LikesVO likesVO = new LikesVO();
        BeanUtils.copyProperties(likes, likesVO);
        likesVO.setUserVO(userVO);
        likesVO.setCommentsVO(commentsVO);
        return likesVO;
    }

    /**
     * 添加点赞
     * @param likesAddRequest 点赞添加请求
     * @return
     */
    @Override
    public boolean addLikes(LikesAddRequest likesAddRequest, HttpServletRequest request) {
        //参数校验
        if(likesAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long commentId = likesAddRequest.getCommentId();

        if(commentId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Likes likes = new Likes();
        User user = userService.getLoginUser(request);
        likes.setCommentId(commentId);
        likes.setUserId(user.getId());
        return this.save(likes);
    }

    /**
     * 删除点赞
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteLikes(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long likesId = deleteRequest.getId();
        return this.removeById(likesId);
    }
}




