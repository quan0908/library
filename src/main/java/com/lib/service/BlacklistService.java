package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.blacklist.BlacklistAddRequest;
import com.lib.model.dto.blacklist.BlacklistQueryRequest;
import com.lib.model.dto.blacklist.BlacklistUpdateRequest;
import com.lib.model.entity.Blacklist;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.model.entity.Blacklist;
import com.lib.model.vo.BlacklistVO;

import java.util.List;

/**
* @author vv
*/
public interface BlacklistService extends IService<Blacklist> {
    /**
     * 获取查询条件
     *
     * @param blacklistQueryRequest 黑名单查询请求
     * @return
     */
    QueryWrapper<Blacklist> getQueryWrapper(BlacklistQueryRequest blacklistQueryRequest);

    /**
     * 获取全部黑名单信息
     *
     * @param blacklistList 黑名单集合
     * @return
     */
    List<BlacklistVO> getBlacklistVO(List<Blacklist> blacklistList);

    /**
     * 获取黑名单新息VO
     *
     * @param blacklist
     * @return
     */
    BlacklistVO getBlacklistVO(Blacklist blacklist);

    /**
     * 添加黑名单
     * @param blacklistAddRequest 黑名单添加请求
     * @return 是否添加成功
     */
    boolean addBlacklist(BlacklistAddRequest blacklistAddRequest);

    /**
     * 修改黑名单
     * @param blacklistUpdateRequest 黑名单修改请求
     * @return 是否修改成功
     */
    boolean updateBlacklist(BlacklistUpdateRequest blacklistUpdateRequest);

    /**
     * 删除黑名单
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteBlacklist(DeleteRequest deleteRequest);
}
