package com.lib.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.model.dto.blacklist.BlacklistAddRequest;
import com.lib.model.dto.blacklist.BlacklistQueryRequest;
import com.lib.model.dto.blacklist.BlacklistUpdateRequest;
import com.lib.model.dto.blacklist.BlacklistAddRequest;
import com.lib.model.dto.blacklist.BlacklistQueryRequest;
import com.lib.model.dto.blacklist.BlacklistUpdateRequest;
import com.lib.model.entity.Blacklist;
import com.lib.model.entity.Blacklist;
import com.lib.model.entity.User;
import com.lib.model.vo.BlacklistVO;
import com.lib.model.vo.BlacklistVO;
import com.lib.model.vo.UserVO;
import com.lib.service.BlacklistService;
import com.lib.mapper.BlacklistMapper;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author vv
*/
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist>
    implements BlacklistService{

    @Resource
    private UserService userService;

    /**
     * 获取黑名单查询条件(被拉进黑名单 userId)
     * @param blacklistQueryRequest 黑名单查询请求
     * @return
     */
    @Override
    public QueryWrapper<Blacklist> getQueryWrapper(BlacklistQueryRequest blacklistQueryRequest) {
        if (blacklistQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long userId = blacklistQueryRequest.getUserId();
        String sortField = blacklistQueryRequest.getSortField();
        String sortOrder = blacklistQueryRequest.getSortOrder();


        QueryWrapper<Blacklist> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userId != null, "blackUserId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<BlacklistVO> getBlacklistVO(List<Blacklist> blacklistList) {
        if (CollectionUtils.isEmpty(blacklistList)) {
            return new ArrayList<>();
        }
        return blacklistList.stream().map(this::getBlacklistVO).collect(Collectors.toList());
    }

    @Override
    public BlacklistVO getBlacklistVO(Blacklist blacklist) {

        if (blacklist == null) {
            return null;
        }
        Long blackUserId = blacklist.getBlackUserId();
        User user = userService.getById(blackUserId);
        BlacklistVO blacklistVO = new BlacklistVO();
        blacklistVO.setBlackUser(user);
        BeanUtils.copyProperties(blacklist, blacklistVO);
        return blacklistVO;
    }

    /**
     * 添加黑名单
     * @param blacklistAddRequest 黑名单添加请求
     * @return
     */
    @Override
    public boolean addBlacklist(BlacklistAddRequest blacklistAddRequest) {
        //参数校验
        if(blacklistAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long userId = blacklistAddRequest.getUserId();

        if(userId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //转成blacklist并加入数据库
        Blacklist blacklist = new Blacklist();
        blacklist.setBlackUserId(userId);
        BeanUtils.copyProperties(blacklistAddRequest,blacklist);
        return this.save(blacklist);
    }

    /**
     * 修改黑名单
     * @param blacklistUpdateRequest 黑名单修改请求
     * @return
     */
    @Override
    public boolean updateBlacklist(BlacklistUpdateRequest blacklistUpdateRequest) {
        if(blacklistUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long blackListId = blacklistUpdateRequest.getId();
        Long userId = blacklistUpdateRequest.getUserId();

        if(blackListId == null || userId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //查询出原来的数据
        QueryWrapper<Blacklist> blacklistQueryWrapper = new QueryWrapper<>();
        blacklistQueryWrapper.eq("id", blackListId);
        Blacklist blacklist = this.getOne(blacklistQueryWrapper);
        if(blacklist == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //新数据覆盖旧数据
        BeanUtils.copyProperties(blacklistUpdateRequest,blacklist);
        return this.updateById(blacklist);
    }

    /**
     * 删除黑名单
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteBlacklist(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long blacklistId = deleteRequest.getId();
        return this.removeById(blacklistId);
    }
}




