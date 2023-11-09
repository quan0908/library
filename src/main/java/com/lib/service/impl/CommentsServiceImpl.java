package com.lib.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.mapper.CommentsMapper;
import com.lib.mapper.LikeRecordMapper;
import com.lib.model.dto.comments.CommentsAddRequest;
import com.lib.model.dto.comments.CommentsQueryRequest;
import com.lib.model.dto.comments.CommentsUpdateRequest;
import com.lib.model.entity.Book;
import com.lib.model.entity.Comments;
import com.lib.model.entity.LikeRecord;
import com.lib.model.entity.User;
import com.lib.model.vo.BookVO;
import com.lib.model.vo.CommentsVO;
import com.lib.model.vo.UserVO;
import com.lib.service.BookService;
import com.lib.service.CommentsService;
import com.lib.service.LikeRecordService;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author vv
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService{
    @Resource
    private UserService userService;

    @Resource
    private BookService bookService;

    @Resource
    private LikeRecordMapper likeRecordMapper;

    /**
     * 获取查询条件
     * @param commentsQueryRequest 评论查询记录请求
     * @return
     */
    @Override
    public QueryWrapper<Comments> getQueryWrapper(CommentsQueryRequest commentsQueryRequest) {
        if (commentsQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        String content = commentsQueryRequest.getContent();
        Integer isChecked = commentsQueryRequest.getIsChecked();
        Long bookId = commentsQueryRequest.getBookId();
        Long userId = commentsQueryRequest.getUserId();
        String sortField = commentsQueryRequest.getSortField();
        String sortOrder = commentsQueryRequest.getSortOrder();

        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank("content"),"content",content);
        queryWrapper.eq(isChecked != null,"isChecked",isChecked);
        queryWrapper.eq(bookId != null, "bookId", bookId);
        queryWrapper.eq(userId != null, "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取全部的评论记录VO
     *
     * @param commentsList
     * @return
     */
    @Override
    public List<CommentsVO> getCommentsVO(List<Comments> commentsList, HttpServletRequest request) {

        if (CollectionUtils.isEmpty(commentsList)) {
            return new ArrayList<>();
        }
        return commentsList.stream().map(item ->
                this.getCommentsVO(item, request)
        ).collect(Collectors.toList());
    }

    /**
     * 获取评论记录VO
     *
     * @param comments
     * @return
     */
    @Override
    public CommentsVO getCommentsVO(Comments comments, HttpServletRequest request) {
        if (comments == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //获取commentVO
        if (this.getById(comments) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        CommentsVO commentsVO = CommentsVO.objToVo(comments);

        //获取userVO
        Long userId = comments.getUserId();
        User user = userService.getById(userId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        //获取checkUsrVO
        Long checkUserId = comments.getCheckUserId();
        User checkUser = userService.getById(checkUserId);
//        if(checkUser == null){
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
        UserVO checkUserVO = UserVO.objToVo(checkUser);

        //获取bookVO
        Long bookId = comments.getBookId();
        Book book = bookService.getById(bookId);
        if(book == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        BookVO bookVO = BookVO.objToVo(book);
        
        commentsVO.setUserVO(userVO);
        commentsVO.setBookVO(bookVO);
        commentsVO.setCheckUserVO(checkUserVO);
        User loginUser = userService.getLoginUser(request);
        //从记录表中查询出来
        if(loginUser!=null){
            QueryWrapper<LikeRecord> likeRecordQueryWrapper = new QueryWrapper<>();
            likeRecordQueryWrapper.eq("commentId",comments.getId());
            likeRecordQueryWrapper.eq("userId",loginUser.getId());
            LikeRecord likeRecord =likeRecordMapper.selectOne(likeRecordQueryWrapper);

            commentsVO.setLike(likeRecord != null);
        }

        return commentsVO;
    }

    /**
     * 添加评论记录
     * @param commentsAddRequest 添加评论记录请求
     * @return
     */
    @Override
    public boolean addComments(CommentsAddRequest commentsAddRequest,HttpServletRequest request) {
        //参数校验
        if(commentsAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long bookId = commentsAddRequest.getBookId();
        String content = commentsAddRequest.getContent();

        //content为空
        if(StringUtils.isBlank(content)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断这本书在数据库中是否存在
        if(bookService.getById(bookId) == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取当前id
        User user = userService.getLoginUser(request);
        
        //加入数据库
        Comments comments = new Comments();
        comments.setContent(content);
        comments.setBookId(bookId);
        comments.setUserId(user.getId());
        return this.save(comments);
    }

    /**
     * 修改评论(管理员审核后进行修改)
     * @param commentsUpdateRequest 评论修改请求
     * @return
     */
    @Override
    public boolean updateComments(CommentsUpdateRequest commentsUpdateRequest,HttpServletRequest request) {
        Comments comments = new Comments();
        BeanUtil.copyProperties(commentsUpdateRequest,comments);
        return updateById(comments);
    }

    /**
     * 删除评论记录
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteComments(DeleteRequest deleteRequest) {
        if (deleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long bookId = deleteRequest.getId();
        return this.removeById(bookId);
    }

    @Override
    public boolean passComment(Long id, HttpServletRequest request) {
        Comments comments = new Comments();
        comments.setId(id);
        comments.setIsChecked(1);
        User loginUser = userService.getLoginUser(request);
        comments.setCheckUserId(loginUser.getId());
        return updateById(comments);
    }

    @Override
    public boolean unPassComment(Long id, HttpServletRequest request) {
        Comments comments = new Comments();
        comments.setId(id);
        comments.setIsChecked(2);
        User loginUser = userService.getLoginUser(request);
        comments.setCheckUserId(loginUser.getId());
        return updateById(comments);
    }
}




