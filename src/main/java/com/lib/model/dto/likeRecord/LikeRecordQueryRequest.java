package com.lib.model.dto.likeRecord;



import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞记录查询请求
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LikeRecordQueryRequest extends PageRequest implements Serializable {
    /**
     * 用户账号
     */
    private String account;

    /**
     * 点赞状态 0-点赞 1-取消点赞
     */
    private Integer status;
    private static final long serialVersionUID = 1L;

}