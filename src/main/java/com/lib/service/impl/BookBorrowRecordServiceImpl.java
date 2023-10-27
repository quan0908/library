package com.lib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.model.entity.BookBorrowRecord;
import com.lib.service.BookBorrowRecordService;
import com.lib.mapper.BookBorrowRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author vv
*/
@Service
public class BookBorrowRecordServiceImpl extends ServiceImpl<BookBorrowRecordMapper, BookBorrowRecord>
    implements BookBorrowRecordService{

}




