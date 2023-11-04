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
import com.lib.model.dto.meetingRoom.MeetingRoomAddRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomQueryRequest;
import com.lib.model.dto.meetingRoom.MeetingRoomUpdateRequest;
import com.lib.model.entity.MeetingRoom;
import com.lib.model.vo.MeetingRoomVO;
import com.lib.service.MeetingRoomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 会议室接口
 * @author quan
 */
@RestController
@RequestMapping("/meetingRoom")
public class MeetingRoomController {
    @Resource
    private MeetingRoomService meetingRoomService;

    /**
     * 分页获取会议室列表
     *
     * @param meetingRoomQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.MEETING_ROOM_ADMIN)
    public BaseResponse<Page<MeetingRoom>> listMeetingRoomByPage(@RequestBody MeetingRoomQueryRequest meetingRoomQueryRequest,
                                                   HttpServletRequest request) {
        long current = meetingRoomQueryRequest.getCurrent();
        long size = meetingRoomQueryRequest.getPageSize();

        Page<MeetingRoom> meetingRoomPage = meetingRoomService.page(new Page<>(current, size),
                meetingRoomService.getQueryWrapper(meetingRoomQueryRequest));

        return ResultUtils.success(meetingRoomPage);
    }

    /**
     * 分页获取会议室封装列表
     *
     * @param meetingRoomQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<MeetingRoomVO>> listMeetingRoomVOByPage(@RequestBody MeetingRoomQueryRequest meetingRoomQueryRequest,
                                                       HttpServletRequest request) {
        if (meetingRoomQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = meetingRoomQueryRequest.getCurrent();
        long size = meetingRoomQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<MeetingRoom> meetingRoomPage = meetingRoomService.page(new Page<>(current, size),
                meetingRoomService.getQueryWrapper(meetingRoomQueryRequest));
        Page<MeetingRoomVO> meetingRoomVOPage = new Page<>(current, size, meetingRoomPage.getTotal());
        List<MeetingRoomVO> meetingRoomVO = meetingRoomService.getMeetingRoomVO(meetingRoomPage.getRecords());
        meetingRoomVOPage.setRecords(meetingRoomVO);
        return ResultUtils.success(meetingRoomVOPage);
    }

    /**
     * 添加会议室
     * @param meetingRoomAddRequest 会议室添加请求
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.MEETING_ROOM_ADMIN)
    public BaseResponse<Void> addMeetingRoom(@RequestBody MeetingRoomAddRequest meetingRoomAddRequest){
        if(meetingRoomService.addMeetingRoom(meetingRoomAddRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改会议室
     * @param meetingRoomUpdateRequest 会议室修改请求
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.MEETING_ROOM_ADMIN)
    public BaseResponse<Void> updateMeetingRoom(@RequestBody MeetingRoomUpdateRequest meetingRoomUpdateRequest){
        if(meetingRoomService.updateMeetingRoom(meetingRoomUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除会议室
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.MEETING_ROOM_ADMIN)
    public BaseResponse<Void> deleteMeetingRoom(@RequestBody DeleteRequest deleteRequest){
        if(meetingRoomService.deleteMeetingRoom(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
}
