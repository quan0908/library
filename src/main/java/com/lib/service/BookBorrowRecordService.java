package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordAddRequest;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordQueryRequest;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordUpdateRequest;
import com.lib.model.entity.BookBorrowRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.model.vo.BookBorrowRecordVO;

import java.util.List;

/**
* @author vv
*/
public interface BookBorrowRecordService extends IService<BookBorrowRecord> {
    /**
     * 获取查询条件
     *
     * @param bookQueryRequest 图书查询请求
     * @return
     */
    QueryWrapper<BookBorrowRecord> getQueryWrapper(BookBorrowRecordQueryRequest bookQueryRequest);

    /**
     * 获取图书信息
     *
     * @param bookList 图书集合
     * @return
     */
    List<BookBorrowRecordVO> getBookBorrowRecordVO(List<BookBorrowRecord> bookList);

    /**
     * 获取图书信息
     *
     * @param book
     * @return
     */
    BookBorrowRecordVO getBookBorrowRecordVO(BookBorrowRecord book);

    /**
     * 添加借书记录
     * @param bookAddRequest 借书记录请求
     * @return 是否添加成功
     */
    boolean addBookBorrowRecord(BookBorrowRecordAddRequest bookAddRequest);

    /**
     * 修改图书
     * @param bookUpdateRequest 图书修改请求
     * @return 是否修改成功
     */
    boolean updateBookBorrowRecord(BookBorrowRecordUpdateRequest bookUpdateRequest);

    /**
     * 删除图书
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteBookBorrowRecord(DeleteRequest deleteRequest);
}
