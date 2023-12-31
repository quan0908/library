package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.likeRecord.LikeRecordAddRequest;
import com.lib.model.dto.likeRecord.LikeRecordCancelRequest;
import com.lib.model.dto.likeRecord.LikeRecordQueryRequest;
import com.lib.model.entity.LikeRecord;
import com.lib.model.entity.User;
import com.lib.model.vo.LikeRecordVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author quan
*/
public interface LikeRecordService extends IService<LikeRecord> {
    /**
     * 获取查询条件
     *
     * @param likeRecordQueryRequest 点赞记录查询请求
     * @return
     */
    QueryWrapper<LikeRecord> getQueryWrapper(LikeRecordQueryRequest likeRecordQueryRequest);

    /**
     * 获取点赞信息
     *
     * @param likeRecordList 点赞记录集合
     * @return
     */
    List<LikeRecordVO> getLikeRecordVO(List<LikeRecord> likeRecordList,HttpServletRequest request);

    /**
     * 获取点赞记录信息
     *
     * @param likeRecord
     * @return
     */
    LikeRecordVO getLikeRecordVO(LikeRecord likeRecord,HttpServletRequest request);

    /**
     * 添加点赞记录
     * @param likeRecordAddRequest 点赞记录添加请求
     * @return 是否添加成功
     */
    boolean addLikeRecord(LikeRecordAddRequest likeRecordAddRequest,HttpServletRequest request);

    /**
     * 删除点赞
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteLikeRecord(DeleteRequest deleteRequest);



    int doCommentLike(long commentId, User loginUser);


    int doCommentLikeInner(long userId, long commentId);
}
