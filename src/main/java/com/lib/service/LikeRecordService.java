package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.likeRecord.LikeRecordAddRequest;
import com.lib.model.dto.likeRecord.LikeRecordQueryRequest;
import com.lib.model.dto.likes.LikesAddRequest;
import com.lib.model.dto.likes.LikesCancelRequest;
import com.lib.model.entity.LikeRecord;
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
     * @param meetingRecordQueryRequest 点赞记录查询请求
     * @return
     */
    QueryWrapper<LikeRecord> getQueryWrapper(LikeRecordQueryRequest meetingRecordQueryRequest);

    /**
     * 获取点赞信息
     *
     * @param meetingRecordList 点赞记录集合
     * @return
     */
    List<LikeRecordVO> getLikeRecordVO(List<LikeRecord> meetingRecordList);

    /**
     * 获取点赞记录信息
     *
     * @param meetingRecord
     * @return
     */
    LikeRecordVO getLikeRecordVO(LikeRecord meetingRecord);

    /**
     * 添加点赞记录
     * @param meetingRecordAddRequest 点赞记录请求
     * @return 是否添加成功
     */
    boolean addLikeRecord(LikeRecordAddRequest meetingRecordAddRequest);

    /**
     * 点赞
     * @param likesAddRequest 点赞添加请求
     * @param request
     * @return
     */
    boolean like(LikesAddRequest likesAddRequest,HttpServletRequest request);

    /**
     * 取消点赞
     * @param likesCancelRequest 点赞取消请求
     * @param request
     * @return
     */
    boolean cancelLike(LikesCancelRequest likesCancelRequest,HttpServletRequest request);

    /**
     * 删除点赞
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteLikeRecord(DeleteRequest deleteRequest);
}
