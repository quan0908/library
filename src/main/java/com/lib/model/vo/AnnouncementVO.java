package com.lib.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.lib.model.entity.Announcement;
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
    public static Announcement voToObj(AnnouncementVO announcementVO) {
        if (announcementVO == null) {
            return null;
        }
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(announcementVO, announcement);
        
        return announcement;
    }

    /**
     * 对象转包装类
     *
     * @param announcement
     * @return
     */
    public static AnnouncementVO objToVo(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        AnnouncementVO announcementVO = new AnnouncementVO();
        BeanUtils.copyProperties(announcement, announcementVO);
        return announcementVO;
    }

}
