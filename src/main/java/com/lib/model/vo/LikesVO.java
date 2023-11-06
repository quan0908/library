package com.lib.model.vo;

import com.lib.model.entity.Likes;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 点赞VO
 */
@Data
public class LikesVO implements Serializable {
    /**
     * 点赞id
     */
    private Long id;

    /**
     * 用户VO
     */
    private UserVO userVO;

    /**
     * commentVO
     */
    private CommentsVO commentsVO;
    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param likesVO
     * @return
     */
    public static Likes voToObj(LikesVO likesVO) {
        if (likesVO == null) {
            return null;
        }
        Likes likes = new Likes();
        BeanUtils.copyProperties(likesVO, likes);

        return likes;
    }

    /**
     * 对象转包装类
     *
     * @param likes
     * @return
     */
    public static LikesVO objToVo(Likes likes) {
        if (likes == null) {
            return null;
        }
        LikesVO likesVO = new LikesVO();
        BeanUtils.copyProperties(likes, likesVO);
        return likesVO;
    }
}
