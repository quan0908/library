package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.mapper.LikeRecordMapper;
import com.lib.model.dto.likeRecord.*;
import com.lib.model.entity.Comments;
import com.lib.model.entity.LikeRecord;
import com.lib.model.entity.User;
import com.lib.model.vo.CommentsVO;
import com.lib.model.vo.LikeRecordVO;
import com.lib.model.vo.UserVO;
import com.lib.service.CommentsService;
import com.lib.service.LikeRecordService;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
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
    private CommentsService commentsService;


    /**
     * 获取点赞查询条件(根据用户id)
     * @param likeRecordQueryRequest 点赞查询请求
     * @return
     */
    @Override
    public QueryWrapper<LikeRecord> getQueryWrapper(LikeRecordQueryRequest likeRecordQueryRequest) {
        if (likeRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long userId = likeRecordQueryRequest.getUserId();
        String sortField = likeRecordQueryRequest.getSortField();
        String sortOrder = likeRecordQueryRequest.getSortOrder();

        QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userId != null,"userId",userId);
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
        if (likeRecord == null) {
            return null;
        }

        Long commentId = likeRecord.getCommentId();
        Long userId = likeRecord.getUserId();
        if(commentId == null || userId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //转成userVO
        User user = userService.getById(userId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        //转成commentVO
        Comments comments = commentsService.getById(commentId);
        if(comments == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CommentsVO commentsVO = commentsService.getCommentsVO(comments);
        LikeRecordVO likeRecordVO = new LikeRecordVO();
        BeanUtils.copyProperties(likeRecord,likeRecordVO);
        likeRecordVO.setUserVO(userVO);
        likeRecordVO.setCommentsVO(commentsVO);
        return likeRecordVO;
    }

    /**
     * 添加点赞
     * @param likeRecordAddRequest 点赞添加请求
     * @return
     */
    @Override
    public boolean addLikeRecord(LikeRecordAddRequest likeRecordAddRequest,HttpServletRequest request) {
        //参数校验
        if(likeRecordAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long commentId = likeRecordAddRequest.getCommentId();
        if(commentId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setCommentId(commentId);
        User user = userService.getLoginUser(request);
        likeRecord.setUserId(user.getId());
        return this.save(likeRecord);
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

    /**
     * 点赞
     * @param likeRecordAddRequest 点赞记录添加请求
     * @param request
     * @return
     */
    @Override
    public boolean like(LikeRecordAddRequest likeRecordAddRequest, HttpServletRequest request) {
        if(likeRecordAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long commentId = likeRecordAddRequest.getCommentId();
        if(commentId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //添加点赞记录
        if(!this.addLikeRecord(likeRecordAddRequest,request)){
            return false;
        }

        //评论点赞+1
        Comments comments = commentsService.getById(commentId);
        comments.setLikeNumber(comments.getLikeNumber() + 1);
        return commentsService.updateById(comments);
    }

    /**
     * 取消点赞
     * @param likeRecordCancelRequest 点赞取消请求
     * @param request
     * @return
     */
    @Override
    public boolean cancelLike(LikeRecordCancelRequest likeRecordCancelRequest, HttpServletRequest request) {
        if(likeRecordCancelRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long commentId = likeRecordCancelRequest.getCommentId();
        if(commentId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        Long userId = user.getId();
        QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        queryWrapper.eq("commentId",commentId);
        LikeRecord likeRecord = this.getOne(queryWrapper);

        //删除点赞记录
        Long id = likeRecord.getId();
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setId(id);
        if(!this.deleteLikeRecord(deleteRequest)){
            return false;
        }

        //评论点赞减1
        Comments comments = commentsService.getById(commentId);
        comments.setLikeNumber(comments.getLikeNumber() - 1);
        return commentsService.updateById(comments);
    }
}




