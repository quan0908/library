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
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordAddRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordQueryRequest;
import com.lib.model.dto.meetingRoomBorrow.MeetingRoomBorrowRecordUpdateRequest;
import com.lib.model.entity.MeetingRoomBorrowRecord;
import com.lib.model.vo.MeetingRoomBorrowRecordVO;
import com.lib.service.MeetingRoomBorrowRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 借会议室记录接口
 * @author quan
 */
@RestController
@RequestMapping("/borrowRecord/meetingRoom")
public class MeetingRoomBorrowController {
    @Resource
    private MeetingRoomBorrowRecordService meetingRoomBorrowRecordService;

    @PostMapping("/list/page")
    public BaseResponse<Page<MeetingRoomBorrowRecord>> listMeetingRoomBorrowRecordByPage(@RequestBody MeetingRoomBorrowRecordQueryRequest
                                                                                   meetingRoomBorrowRecordQueryRequest,
                                                                                         HttpServletRequest request) {
        long current = meetingRoomBorrowRecordQueryRequest.getCurrent();
        long size = meetingRoomBorrowRecordQueryRequest.getPageSize();
        Page<MeetingRoomBorrowRecord> meetingRoomBorrowRecordPage = meetingRoomBorrowRecordService.page(new Page<>(current, size),
                meetingRoomBorrowRecordService.getQueryWrapper(meetingRoomBorrowRecordQueryRequest));
        return ResultUtils.success(meetingRoomBorrowRecordPage);
    }

    /**
     * 分页获取借会议室记录列表
     *
     * @param meetingRoomBorrowRecordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<MeetingRoomBorrowRecordVO>> listMeetingRoomBorrowRecordVOByPage(@RequestBody MeetingRoomBorrowRecordQueryRequest 
                                                                                                         meetingRoomBorrowRecordQueryRequest,
                                                                                             HttpServletRequest request) {
        if (meetingRoomBorrowRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = meetingRoomBorrowRecordQueryRequest.getCurrent();
        long size = meetingRoomBorrowRecordQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<MeetingRoomBorrowRecord> meetingRoomBorrowRecordPage = meetingRoomBorrowRecordService.page(new Page<>(current, size),
                meetingRoomBorrowRecordService.getQueryWrapper(meetingRoomBorrowRecordQueryRequest));
        Page<MeetingRoomBorrowRecordVO> meetingRoomBorrowRecordVOPage = new Page<>(current, size, meetingRoomBorrowRecordPage.getTotal());
        List<MeetingRoomBorrowRecordVO> meetingRoomVO = meetingRoomBorrowRecordService.getMeetingRoomBorrowRecordVO(meetingRoomBorrowRecordPage.getRecords());
        meetingRoomBorrowRecordVOPage.setRecords(meetingRoomVO);
        return ResultUtils.success(meetingRoomBorrowRecordVOPage);
    }

    /**
     * 添加会议室记录
     * @param meetingRoomBorrowRecordAddRequest 借会议室记录添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addMeetingRoomBorrowRecord(@RequestBody MeetingRoomBorrowRecordAddRequest meetingRoomBorrowRecordAddRequest,
                                                  HttpServletRequest request){
        if(meetingRoomBorrowRecordService.addMeetingRoomBorrowRecord(meetingRoomBorrowRecordAddRequest,request)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改借会议室记录
     * @param meetingRoomBorrowRecordUpdateRequest 借会议室记录修改请求
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Void> updateMeetingRoomBorrowRecord(@RequestBody MeetingRoomBorrowRecordUpdateRequest meetingRoomBorrowRecordUpdateRequest){
        if(meetingRoomBorrowRecordService.updateMeetingRoomBorrowRecord(meetingRoomBorrowRecordUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除借会议室记录
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.MEETING_ROOM_ADMIN)
    public BaseResponse<Void> deleteMeetingRoomBorrowRecord(@RequestBody DeleteRequest deleteRequest){
        if(meetingRoomBorrowRecordService.deleteMeetingRoomBorrowRecord(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }

    @GetMapping("/get/owner")
    public BaseResponse<Long> getMeetingRoomBorrowOwner(String meetingRoomId){
        Long userId = meetingRoomBorrowRecordService.getMeetingRoomBorrowOwner(meetingRoomId);
        if(userId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"该会议室空闲");
    }
}
