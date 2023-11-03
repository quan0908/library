package com.lib.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lib.common.DeleteRequest;
import com.lib.model.dto.book.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.model.entity.Book;
import com.lib.model.vo.BookVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author vv
*/
public interface BookService extends IService<Book> {
    /**
     * 获取查询条件
     *
     * @param bookQueryRequest 图书查询请求
     * @return
     */
    QueryWrapper<Book> getQueryWrapper(BookQueryRequest bookQueryRequest);

    /**
     * 获取全部图书信息
     *
     * @param bookList 图书集合
     * @return
     */
    List<BookVO> getBookVO(List<Book> bookList);

    /**
     * 获取图书信息VO
     *
     * @param book
     * @return
     */
    BookVO getBookVO(Book book);

    /**
     * 添加图书
     * @param bookAddRequest 图书添加请求
     * @return 是否添加成功
     */
    boolean addBook(BookAddRequest bookAddRequest);

    /**
     * 修改图书
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
