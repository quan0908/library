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
import com.lib.model.dto.meetingRecord.MeetingRecordAddRequest;
import com.lib.model.dto.meetingRecord.MeetingRecordQueryRequest;
import com.lib.model.dto.meetingRecord.MeetingRecordUpdateRequest;
import com.lib.model.entity.MeetingRecord;
import com.lib.model.vo.MeetingRecordVO;
import com.lib.service.MeetingRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/meetingRecord")
public class MeetingRecordController {
    @Resource
    private MeetingRecordService meetingRecordService;
    
    /**
     * 分页获取会议记录列表
     *
     * @param meetingRecordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page") 
    public BaseResponse<Page<MeetingRecord>> listMeetingRecordByPage(@RequestBody MeetingRecordQueryRequest meetingRecordQueryRequest,
                                                   HttpServletRequest request) {
        long current = meetingRecordQueryRequest.getCurrent();
        long size = meetingRecordQueryRequest.getPageSize();

        Page<MeetingRecord> meetingRecordPage = meetingRecordService.page(new Page<>(current, size),
                meetingRecordService.getQueryWrapper(meetingRecordQueryRequest));

        return ResultUtils.success(meetingRecordPage);
    }

    /**
     * 分页获取会议记录封装列表
     * @param meetingRecordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<MeetingRecordVO>> listMeetingRecordVOByPage(@RequestBody MeetingRecordQueryRequest meetingRecordQueryRequest,
                                                       HttpServletRequest request) {
        if (meetingRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = meetingRecordQueryRequest.getCurrent();
        long size = meetingRecordQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<MeetingRecord> meetingRecordPage = meetingRecordService.page(new Page<>(current, size),
                meetingRecordService.getQueryWrapper(meetingRecordQueryRequest));
        Page<MeetingRecordVO> meetingRecordVOPage = new Page<>(current, size, meetingRecordPage.getTotal());
        List<MeetingRecordVO> meetingRecordVO = meetingRecordService.getMeetingRecordVO(meetingRecordPage.getRecords());
        meetingRecordVOPage.setRecords(meetingRecordVO);
        return ResultUtils.success(meetingRecordVOPage);
    }

    /**
     * 添加会议记录
     * @param meetingRecordAddRequest 会议记录添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addMeetingRecord(@RequestBody MeetingRecordAddRequest meetingRecordAddRequest){
        if(meetingRecordService.addMeetingRecord(meetingRecordAddRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改会议记录
     * @param meetingRecordUpdateRequest 会议记录修改请求
     * @return
     */
    @PostMapping("/update") 
    public BaseResponse<Void> updateMeetingRecord(@RequestBody MeetingRecordUpdateRequest meetingRecordUpdateRequest){
        if(meetingRecordService.updateMeetingRecord(meetingRecordUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除会议记录
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete") 
    public BaseResponse<Void> deleteMeetingRecord(@RequestBody DeleteRequest deleteRequest){
        if(meetingRecordService.deleteMeetingRecord(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
}
