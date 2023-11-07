package com.lib.model.vo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 点赞记录VO
 */
@Data
public class LikeRecordVO implements Serializable {
    /**
     * 点赞id
     */
    private Long id;

    /**
     * 评论VO
     */
    private CommentsVO commentsVO;

    /**
     * 用户VO
     */
    private UserVO userVO;
    private static final long serialVersionUID = 1L;
}
