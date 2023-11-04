package com.lib.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lib.model.entity.Meeting;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议记录VO
 */
@Data
public class MeetingRecordVO implements Serializable {
    /**
     * 会议记录id
     */
    private Long id;

    /**
     * 会议VO
     */
    private MeetingVO meetingVO;

    /**
     * 参与人VO
     */
    private UserVO participantVO;

    /**
     * 0-未审核 1-同意 2-不同意
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}
