package com.lib.model.dto.meetingRoom;
import com.lib.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
/**
 * 会议查询请求
 * @author quan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MeetingRoomQueryRequest extends PageRequest implements Serializable {
    /**
     * 会议室名字
     */
    private String name;

    /**
     * 会议室是否空
     */
    private Integer isEmpty;
    private static final long serialVersionUID = 1L;
}