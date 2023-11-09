package com.lib.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lib.model.entity.Comments;
import com.lib.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
@Data
/**
 * @author quan
 *评论 VO
 */
public class CommentsVO implements Serializable {
    /**
     * 评论id
     */
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户VO
     */
    private UserVO userVO;

    /**
     * 图书VO
     */
    private BookVO bookVO;

    /**
     * 审核人VO
     */
    private UserVO checkUserVO;

    /**
     * 评论点赞数量
     */
    private Integer likeNumber;

    private boolean isLike;

    /**
     * 0-待审核 1-审核通过 2-审核未通过
     */
    private Integer isChecked;

    /**
     * 包装类转对象
     *
     * @param commentsVO
     * @return
     */
    public static Comments voToObj(CommentsVO commentsVO) {
        if (commentsVO == null) {
            return null;
        }
        Comments comments = new Comments();
        BeanUtils.copyProperties(commentsVO, comments);

        return comments;
    }

    /**
     * 对象转包装类
     *
     * @param comments
     * @return
     */
    public static CommentsVO objToVo(Comments comments) {
        if (comments == null) {
            return null;
        }
        CommentsVO commentsVO = new CommentsVO();
        BeanUtils.copyProperties(comments, commentsVO);
        return commentsVO;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
