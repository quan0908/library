package com.lib.model.vo;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;

/**
 * 点赞记录VO
 */
@Data
public class LikeRecordVO implements Serializable {
    /**
     * 点赞记录id
     */
    private Long id;

    /**
     * 点赞VO
     */
    private LikesVO likesVO;

    /**
     * 点赞状态 0-点赞 1-取消点赞
     */
    private Integer status;
    private static final long serialVersionUID = 1L;
}
