package com.lib.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.lib.model.entity.Appeal;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告VO
 * @author quan
 */
@Data
public class AnnouncementVO implements Serializable {
    /**
     * 公告id
     */
    private Long id;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告发布人
     */
    private UserVO userVO;

    /**
     * 创建时间
     */
    private Date createTime;
    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param announcementVO
     * @return
     */
    public static Appeal voToObj(AnnouncementVO announcementVO) {
        if (announcementVO == null) {
            return null;
        }
        Appeal announcement = new Appeal();
        BeanUtils.copyProperties(announcementVO, announcement);
        
        return announcement;
    }

    /**
     * 对象转包装类
     *
     * @param announcement
     * @return
     */
    public static AnnouncementVO objToVo(Appeal announcement) {
        if (announcement == null) {
            return null;
        }
        AnnouncementVO announcementVO = new AnnouncementVO();
        BeanUtils.copyProperties(announcement, announcementVO);
        return announcementVO;
    }

}
