package com.lib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.model.entity.Book;
import com.lib.service.BookService;
import com.lib.mapper.BookMapper;
import org.springframework.stereotype.Service;

/**
* @author vv
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{

}




