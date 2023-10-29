package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.model.dto.book.BookAddRequest;
import com.lib.model.dto.book.BookQueryRequest;
import com.lib.model.dto.book.BookUpdateRequest;
import com.lib.model.entity.Book;
import com.lib.model.vo.BookVO;
import com.lib.service.BookService;
import com.lib.mapper.BookMapper;
import com.lib.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author vv
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{
    /**
     * 获取图书查询条件(图书名，图书类型，图书作者)
     * @param bookQueryRequest 图书查询请求
     * @return
     */
    @Override
    public QueryWrapper<Book> getQueryWrapper(BookQueryRequest bookQueryRequest) {
        if (bookQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        String bookName = bookQueryRequest.getBookName();
        String type = bookQueryRequest.getType();
        String bookAuthor = bookQueryRequest.getBookAuthor();
        String sortField = bookQueryRequest.getSortField();
        String sortOrder = bookQueryRequest.getSortOrder();


        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(bookName), "bookName", bookName);
        queryWrapper.like(StringUtils.isNotBlank(type), "type", type);
        queryWrapper.eq(StringUtils.isNotBlank(bookAuthor),"bookAuthor",bookAuthor);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<BookVO> getBookVO(List<Book> bookList) {
        if (CollectionUtils.isEmpty(bookList)) {
            return new ArrayList<>();
        }
        return bookList.stream().map(this::getBookVO).collect(Collectors.toList());
    }

    @Override
    public BookVO getBookVO(Book book) {
        if (book == null) {
            return null;
        }
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(book, bookVO);
        return bookVO;
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
        String bookAuthor = bookAddRequest.getBookAuthor();
        String bookTra = bookAddRequest.getBookTra();
        String bookCover = bookAddRequest.getBookCover();

        if(StringUtils.isAnyBlank(bookName,bookLocation,type,bookAuthor,bookTra,bookCover)){
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
        String bookAuthor = bookUpdateRequest.getBookAuthor();
        String bookTra = bookUpdateRequest.getBookTra();
        String bookCover = bookUpdateRequest.getBookCover();

        if(StringUtils.isAnyBlank(bookName,bookLocation,type,bookAuthor,bookTra,bookCover)){
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




