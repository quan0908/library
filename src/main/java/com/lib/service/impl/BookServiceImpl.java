package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.exception.BusinessException;
import com.lib.model.dto.book.BookAddRequest;
import com.lib.model.dto.book.BookUpdateRequest;
import com.lib.model.entity.Book;
import com.lib.model.vo.BookVO;
import com.lib.service.BookService;
import com.lib.mapper.BookMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author vv
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{
    /**
     * 获取全部图书
     * @return
     */
    @Override
    public List<BookVO> getBooks() {
        List<Book> bookList = this.list();
        //将bookList中的book转成bookVo
        List<BookVO> bookVOList = new ArrayList<>();
        for(Book book : bookList){
            bookVOList.add(BookVO.objToVo(book));
        }
        return bookVOList;
    }

    /**
     * 添加图书
     * @param bookAddRequest 图书添加请求
     * @return
     */
    @Override
    public boolean addBook(BookAddRequest bookAddRequest) {
        //参数校验
        if(bookAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String bookName = bookAddRequest.getBookName();
        String bookLocation = bookAddRequest.getBookLocation();
        String type = bookAddRequest.getType();
        Integer bookNumber = bookAddRequest.getBookNumber();

        if(StringUtils.isAnyBlank(bookName,bookLocation,type)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //书本数量小于0
        if(bookNumber < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //转成book并加入数据库
        Book book = new Book();
        BeanUtils.copyProperties(bookAddRequest,book);
        return this.save(book);
    }

    /**
     * 修改图书
     * @param bookUpdateRequest 图书修改请求
     * @return
     */
    @Override
    public boolean updateBook(BookUpdateRequest bookUpdateRequest) {
        if(bookUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long bookId = bookUpdateRequest.getBookId();
        String bookName = bookUpdateRequest.getBookName();
        String bookLocation = bookUpdateRequest.getBookLocation();
        String type = bookUpdateRequest.getType();
        Integer bookNumber = bookUpdateRequest.getBookNumber();

        if(StringUtils.isAnyBlank(bookName,bookLocation,type)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //书本数量小于0
        if(bookNumber < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //查询出原来的数据
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("id", bookId);
        Book book = this.getOne(bookQueryWrapper);
        if(book == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //新数据覆盖旧数据
        BeanUtils.copyProperties(bookUpdateRequest,book);
        return this.updateById(book);
    }

    /**
     * 删除图书
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteBook(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long bookId = deleteRequest.getId();
        return this.removeById(bookId);
    }


}




