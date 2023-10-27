package com.lib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.model.entity.Comments;
import com.lib.service.CommentsService;
import com.lib.mapper.CommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author vv
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService{

}




