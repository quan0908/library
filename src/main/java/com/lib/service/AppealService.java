package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.appeal.AppealAddRequest;
import com.lib.model.dto.appeal.AppealQueryRequest;
import com.lib.model.dto.appeal.AppealUpdateRequest;
import com.lib.model.dto.appeal.AppealAddRequest;
import com.lib.model.entity.Appeal;
import com.lib.model.entity.Appeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.model.vo.AppealVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author zyz19
* @description 针对表【appeal】的数据库操作Service
* @createDate 2023-11-09 12:06:13
*/
public interface AppealService extends IService<Appeal> {
    boolean addAppeal(AppealAddRequest appealAddRequest);

     
    QueryWrapper<Appeal> getQueryWrapper(AppealQueryRequest appealQueryRequest);


    List<AppealVO> getAppealVO(List<Appeal> appealList);


    AppealVO getAppealVO(Appeal appeal);



    boolean updateAppeal(AppealUpdateRequest appealUpdateRequest);


    boolean deleteAppeal(DeleteRequest deleteRequest);
}
