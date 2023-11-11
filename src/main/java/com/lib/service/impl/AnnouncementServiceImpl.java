package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.mapper.AnnouncementMapper;
import com.lib.model.dto.announcement.AnnouncementAddRequest;
import com.lib.model.dto.announcement.AnnouncementQueryRequest;
import com.lib.model.dto.announcement.AnnouncementUpdateRequest;
import com.lib.model.entity.Announcement;
import com.lib.model.entity.Appeal;
import com.lib.model.entity.User;
import com.lib.model.vo.AnnouncementVO;
import com.lib.model.vo.UserVO;
import com.lib.service.AnnouncementService;
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
* @author quan
*/
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService {
    @Resource
    private UserService userService;

    /**
     * 获取公告查询条件(公告类型，公告作者)
     * @param announcementQueryRequest 公告查询请求
     * @return
     */
    @Override
    public QueryWrapper<Announcement> getQueryWrapper(AnnouncementQueryRequest announcementQueryRequest) {
        if (announcementQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        String content = announcementQueryRequest.getContent();
        String account = announcementQueryRequest.getAccount();

        User user = new User();
        Long creatorId = user.getId();
        if(StringUtils.isNotBlank(account)){
            //根据用户账号查找userId
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account",account);

            user = userService.getOne(queryWrapper);
            if(user == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            creatorId = user.getId();
        }
        String sortField = announcementQueryRequest.getSortField();
        String sortOrder = announcementQueryRequest.getSortOrder();

        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.eq(StringUtils.isNotBlank(account), "creatorId", creatorId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<AnnouncementVO> getAnnouncementVO(List<Announcement> announcementList) {
        if (CollectionUtils.isEmpty(announcementList)) {
            return new ArrayList<>();
        }
        return announcementList.stream().map(this::getAnnouncementVO).collect(Collectors.toList());
    }

    @Override
    public AnnouncementVO getAnnouncementVO(Announcement announcement) {
        if (announcement == null) {
            return null;
        }

        Long creatorId = announcement.getCreatorId();
        User user = userService.getById(creatorId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        AnnouncementVO announcementVO = new AnnouncementVO();
        BeanUtils.copyProperties(announcement, announcementVO);
        announcementVO.setUserVO(userVO);
        return announcementVO;
    }

    /**
     * 添加公告
     * @param announcementAddRequest 公告添加请求
     * @return
     */
    @Override
    public boolean addAnnouncement(AnnouncementAddRequest announcementAddRequest, HttpServletRequest request) {
        //参数校验
        if(announcementAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String content = announcementAddRequest.getContent();

        if(StringUtils.isAnyBlank(content)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //转成announcement并加入数据库
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(announcementAddRequest,announcement);
        User user = userService.getLoginUser(request);
        announcement.setCreatorId(user.getId());
        return this.save(announcement);
    }

    /**
     * 修改公告
     * @param announcementUpdateRequest 公告修改请求
     * @return
     */
    @Override
    public boolean updateAnnouncement(AnnouncementUpdateRequest announcementUpdateRequest) {
        if(announcementUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String content = announcementUpdateRequest.getContent();
        Long id = announcementUpdateRequest.getId();
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(StringUtils.isBlank(content)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(announcementUpdateRequest,announcement);
        return this.updateById(announcement);
    }

    /**
     * 删除公告
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteAnnouncement(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long announcementId = deleteRequest.getId();
        return this.removeById(announcementId);
    }

    /**
     * 获取最新公告
     * @return
     */
    @Override
    public AnnouncementVO getNewAnnouncement() {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("createTime");
        List<Announcement> announcementList = this.list(queryWrapper);
        List<AnnouncementVO> announcementVO = this.getAnnouncementVO(announcementList);
        return announcementVO.get(0);
    }
}




