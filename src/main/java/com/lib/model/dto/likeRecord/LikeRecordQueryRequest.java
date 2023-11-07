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
     * 用户id
     */
    private Long userId;
    private static final long serialVersionUID = 1L;

}