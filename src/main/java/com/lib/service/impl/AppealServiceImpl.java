package com.lib.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.exception.ThrowUtils;
import com.lib.mapper.AppealMapper;
import com.lib.model.dto.appeal.AppealAddRequest;
import com.lib.model.dto.appeal.AppealQueryRequest;
import com.lib.model.dto.appeal.AppealUpdateRequest;
import com.lib.model.entity.Appeal;
import com.lib.model.entity.User;
import com.lib.model.vo.AppealVO;
import com.lib.model.vo.UserVO;
import com.lib.service.AppealService;
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
 * @author zyz19
 * @description 针对表【appeal】的数据库操作Service实现
 * @createDate 2023-11-09 12:06:13
 */
@Service
public class AppealServiceImpl extends ServiceImpl<AppealMapper, Appeal>
        implements AppealService {
    @Resource
    private UserService userService;

    @Override
    public boolean addAppeal(AppealAddRequest appealAddRequest) {

        String idCard = appealAddRequest.getIdCard();
        String username = appealAddRequest.getUsername();
        String account = appealAddRequest.getAccount();
        String password = appealAddRequest.getPassword();


        String userReason = appealAddRequest.getUserReason();

        Long userId = appealAddRequest.getUserId();
        ThrowUtils.throwIf(userId == null || userReason == null
                || idCard == null || username == null || account == null, ErrorCode.PARAMS_ERROR);
        //校验账号、密码、身份证是否同
        User user = userService.getById(userId);
        String encryptPassword = userService.encryptPassword(password);
        if (!user.getPassword().equals(encryptPassword)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        if (!user.getAccount().equals(account)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        if (!user.getUsername().equals(username)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        if (!user.getIdCard().equals(idCard)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        QueryWrapper<Appeal> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        Appeal old = this.getOne(wrapper);

        if (old != null && old.getStatus() != 2) {
            throw new BusinessException(ErrorCode.APPLY_ERROR);
        }

        Appeal appeal = new Appeal();
        BeanUtil.copyProperties(appealAddRequest, appeal);
        return save(appeal);
    }


    @Override
    public QueryWrapper<Appeal> getQueryWrapper(AppealQueryRequest appealQueryRequest) {

        if (appealQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Integer status = appealQueryRequest.getStatus();

        User user = new User();
        Long userId = user.getId();

        String sortField = appealQueryRequest.getSortField();
        String sortOrder = appealQueryRequest.getSortOrder();

        QueryWrapper<Appeal> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(userId != null, "userId", userId);
        queryWrapper.eq(status != null, "status", status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<AppealVO> getAppealVO(List<Appeal> appealList) {
        if (CollectionUtils.isEmpty(appealList)) {
            return new ArrayList<>();
        }
        return appealList.stream().map(this::getAppealVO).collect(Collectors.toList());
    }

    @Override
    public AppealVO getAppealVO(Appeal appeal) {
        if (appeal == null) {
            return null;
        }

        Long userId = appeal.getUserId();
        User user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        AppealVO appealVO = new AppealVO();
        BeanUtils.copyProperties(appeal, appealVO);
        appealVO.setAppealUser(userVO);
        return appealVO;
    }



    @Override
    public boolean updateAppeal(AppealUpdateRequest appealUpdateRequest) {
        if (appealUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        Long id = appealUpdateRequest.getId();
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Appeal appeal = new Appeal();
        BeanUtils.copyProperties(appealUpdateRequest, appeal);
        return this.updateById(appeal);
    }


    @Override
    public boolean deleteAppeal(DeleteRequest deleteRequest) {
        if (deleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long appealId = deleteRequest.getId();
        return this.removeById(appealId);
    }
}




