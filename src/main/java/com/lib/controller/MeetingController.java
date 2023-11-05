package com.lib.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lib.common.BaseResponse;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.common.ResultUtils;
import com.lib.exception.BusinessException;
import com.lib.exception.ThrowUtils;
import com.lib.model.dto.meeting.*;
import com.lib.model.entity.Meeting;
import com.lib.model.vo.MeetingVO;
import com.lib.service.MeetingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping("/meeting")
public class MeetingController {
    @Resource
    private MeetingService meetingService;
    
    /**
     * 分页获取会议列表
     *
     * @param meetingQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page") 
    public BaseResponse<Page<Meeting>> listMeetingByPage(@RequestBody MeetingQueryRequest meetingQueryRequest,
                                                   HttpServletRequest request) {
        long current = meetingQueryRequest.getCurrent();
        long size = meetingQueryRequest.getPageSize();

        Page<Meeting> meetingPage = meetingService.page(new Page<>(current, size),
                meetingService.getQueryWrapper(meetingQueryRequest));

        return ResultUtils.success(meetingPage);
    }

    /**
     * 分页获取会议封装列表
     * @param meetingQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<MeetingVO>> listMeetingVOByPage(@RequestBody MeetingQueryRequest meetingQueryRequest,
                                                       HttpServletRequest request) {
        if (meetingQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = meetingQueryRequest.getCurrent();
        long size = meetingQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Meeting> meetingPage = meetingService.page(new Page<>(current, size),
                meetingService.getQueryWrapper(meetingQueryRequest));
        Page<MeetingVO> meetingVOPage = new Page<>(current, size, meetingPage.getTotal());
        List<MeetingVO> meetingVO = meetingService.getMeetingVO(meetingPage.getRecords());
        meetingVOPage.setRecords(meetingVO);
        return ResultUtils.success(meetingVOPage);
    }

    /**
     * 添加会议
     * @param meetingAddRequest 会议添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addMeeting(@RequestBody MeetingAddRequest meetingAddRequest,
                                                        HttpServletRequest request){
        if(meetingService.addMeeting(meetingAddRequest,request)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改会议
     * @param meetingUpdateRequest 会议修改请求
     * @return
     */
    @PostMapping("/update") 
    public BaseResponse<Void> updateMeeting(@RequestBody MeetingUpdateRequest meetingUpdateRequest){
        if(meetingService.updateMeeting(meetingUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除会议
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete") 
    public BaseResponse<Void> deleteMeeting(@RequestBody DeleteRequest deleteRequest){
        if(meetingService.deleteMeeting(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }

    @GetMapping("/get")
    public BaseResponse<MeetingVO> getMeetingById(Long id){
        if(id == null || id <= 0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Meeting meeting = meetingService.getById(id);
        if(meeting == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        MeetingVO meetingVO = meetingService.getMeetingVO(meeting);

        return ResultUtils.success(meetingVO);
    }
}
