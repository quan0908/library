package com.lib.service;

import com.lib.common.DeleteRequest;
import com.lib.model.dto.book.BookAddRequest;
import com.lib.model.dto.book.BookUpdateRequest;
import com.lib.model.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.model.vo.BookVO;

import java.util.List;

/**
* @author vv
*/
public interface BookService extends IService<Book> {
    /**
     * 查询图书馆内全部图书
     * @return BookVO
     */
    List<BookVO> getBooks();

    /**
     * 添加图书
     * @param bookAddRequest 图书添加请求
     * @return 是否添加成功
     */
    boolean addBook(BookAddRequest bookAddRequest);

    /**
     *
     * @param bookUpdateRequest 图书修改请求
     * @return 是否修改成功
     */
    boolean updateBook(BookUpdateRequest bookUpdateRequest);

    /**
     * 删除图书
     * @param deleteRequest 删除请求
     * @return
     */
    boolean deleteBook(DeleteRequest deleteRequest);
}
