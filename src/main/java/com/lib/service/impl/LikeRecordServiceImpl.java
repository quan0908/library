package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.constant.LikesConstant;
import com.lib.exception.BusinessException;
import com.lib.mapper.LikeRecordMapper;
import com.lib.model.dto.likeRecord.*;
import com.lib.model.dto.likes.LikesAddRequest;
import com.lib.model.dto.likes.LikesCancelRequest;
import com.lib.model.entity.*;
import com.lib.model.vo.LikeRecordVO;
import com.lib.model.vo.LikesVO;
import com.lib.service.LikeRecordService;
import com.lib.service.LikesService;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
import jdk.internal.org.objectweb.asm.Handle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author quan
*/
@Service
public class LikeRecordServiceImpl extends ServiceImpl<LikeRecordMapper, LikeRecord>
    implements LikeRecordService {
    @Resource
    private UserService userService;

    @Resource
    private LikesService likesService;

    /**
     * 获取点赞查询条件(根据点赞用户账号)
     * @param likeRecordQueryRequest 点赞查询请求
     * @return
     */
    @Override
    public QueryWrapper<LikeRecord> getQueryWrapper(LikeRecordQueryRequest likeRecordQueryRequest) {
        if (likeRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        String account = likeRecordQueryRequest.getAccount();
        Integer status = likeRecordQueryRequest.getStatus();
        User user = new User();
        Long userId = user.getId();
        Likes likes = new Likes();
        if(StringUtils.isNotBlank(account)){
            //根据用户账号查找userId
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account",account);
            user = userService.getOne(queryWrapper);
            if(user == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            userId = user.getId();
            QueryWrapper<Likes> likesQueryWrapper = new QueryWrapper<>();
            likesQueryWrapper.eq("userId",userId);
            likes = likesService.getOne(likesQueryWrapper);
        }

        String sortField = likeRecordQueryRequest.getSortField();
        String sortOrder = likeRecordQueryRequest.getSortOrder();

        QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(account),"likesId",likes.getId());
        queryWrapper.eq(status != null,"status",status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<LikeRecordVO> getLikeRecordVO(List<LikeRecord> likeRecordList) {
        if (CollectionUtils.isEmpty(likeRecordList)) {
            return new ArrayList<>();
        }
        return likeRecordList.stream().map(this::getLikeRecordVO).collect(Collectors.toList());
    }

    @Override
    public LikeRecordVO getLikeRecordVO(LikeRecord likeRecord) {
        if(likeRecord == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //获取likesVO
        Long likesId = likeRecord.getLikesId();
        Likes likes = likesService.getById(likesId);

        LikeRecordVO likeRecordVO = new LikeRecordVO();
        BeanUtils.copyProperties(likeRecord,likeRecordVO);
        //设置likesVO
        LikesVO likesVO = likesService.getLikesVO(likes);
        //如果用户取消了点赞，likeVO为空
        if(likes == null){
            return likeRecordVO;
        }
        BeanUtils.copyProperties(likes,likesVO);
        likeRecordVO.setLikesVO(likesVO);
        return likeRecordVO;
    }

    /**
     * 添加点赞记录
     * @param likeRecordAddRequest 点赞记录添加请求
     * @return
     */
    @Override
    public boolean addLikeRecord(LikeRecordAddRequest likeRecordAddRequest) {
        //参数校验
        if(likeRecordAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long likesId = likeRecordAddRequest.getLikesId();
        Integer status = likeRecordAddRequest.getStatus();

        if(likesId == null || status == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setLikesId(likesId);
        likeRecord.setStatus(status);
        return this.save(likeRecord);
    }

    /**
     * 用户点赞
     * @param likesAddRequest
     * @return
     */
    @Override
    public boolean like(LikesAddRequest likesAddRequest,HttpServletRequest request) {
        if(likesAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long commentId = likesAddRequest.getCommentId();
        if(commentId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if(!likesService.addLikes(likesAddRequest, request)){
            return false;
        }

        QueryWrapper<Likes> likesQueryWrapper = new QueryWrapper<>();
        likesQueryWrapper.eq("commentId",commentId);
        likesQueryWrapper.eq("userId",userService.getLoginUser(request).getId());
        Likes likes = likesService.getOne(likesQueryWrapper);
        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setLikesId(likes.getId());
        likeRecord.setStatus(LikesConstant.LIKE);
        return this.save(likeRecord);
    }

    /**
     * 取消点赞
     * @param likesCancelRequest
     * @return
     */
    @Override
    public boolean cancelLike(LikesCancelRequest likesCancelRequest,HttpServletRequest request) {
        if(likesCancelRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long commentId = likesCancelRequest.getCommentId();
        QueryWrapper<Likes> likesQueryWrapper = new QueryWrapper<>();
        likesQueryWrapper.eq("commentId",commentId);
        likesQueryWrapper.eq("userId",userService.getLoginUser(request).getId());
        Likes likes = likesService.getOne(likesQueryWrapper);
        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setLikesId(likes.getId());
        likeRecord.setStatus(LikesConstant.CANCEL);
        this.save(likeRecord);

        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setId(likes.getId());
        return likesService.deleteLikes(deleteRequest);
    }

    /**
     * 删除点赞
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteLikeRecord(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long likeRecordId = deleteRequest.getId();
        return this.removeById(likeRecordId);
    }
}




