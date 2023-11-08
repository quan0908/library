package com.lib.model.dto.meetingRecord;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议记录查询请求
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingRecordQueryRequest extends PageRequest implements Serializable {
    /**
     * 会议室id
     */
    private Long meetingRoomId;

    /**
     * 参与人账号
     */
    private String account;

    /**
     * 0-未审核 1-同意 2-不同意
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

}