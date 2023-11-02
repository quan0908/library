package com.lib.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lib.annotation.AuthCheck;
import com.lib.common.BaseResponse;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.common.ResultUtils;
import com.lib.constant.UserConstant;
import com.lib.exception.BusinessException;
import com.lib.exception.ThrowUtils;
import com.lib.model.dto.book.BookAddRequest;
import com.lib.model.dto.book.BookQueryRequest;
import com.lib.model.dto.book.BookUpdateRequest;
import com.lib.model.entity.Book;
import com.lib.model.vo.BookVO;
import com.lib.service.BookService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图书接口
 * @author quan
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;

    /**
     * 分页获取图书列表
     *
     * @param bookQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Page<Book>> listBookByPage(@RequestBody BookQueryRequest bookQueryRequest,
                                                   HttpServletRequest request) {
        long current = bookQueryRequest.getCurrent();
        long size = bookQueryRequest.getPageSize();

        Page<Book> bookPage = bookService.page(new Page<>(current, size),
                bookService.getQueryWrapper(bookQueryRequest));

        return ResultUtils.success(bookPage);
    }

    /**
     * 分页获取图书封装列表
     * @param bookQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<BookVO>> listBookVOByPage(@RequestBody BookQueryRequest bookQueryRequest,
                                                       HttpServletRequest request) {
        if (bookQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = bookQueryRequest.getCurrent();
        long size = bookQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Book> bookPage = bookService.page(new Page<>(current, size),
                bookService.getQueryWrapper(bookQueryRequest));
        Page<BookVO> bookVOPage = new Page<>(current, size, bookPage.getTotal());
        List<BookVO> bookVO = bookService.getBookVO(bookPage.getRecords());
        bookVOPage.setRecords(bookVO);
        return ResultUtils.success(bookVOPage);
    }

    /**
     * 添加图书
     * @param bookAddRequest 图书添加请求
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Boolean> addBook(@RequestBody BookAddRequest bookAddRequest){
        if(bookService.addBook(bookAddRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改图书
     * @param bookUpdateRequest 图书修改请求
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Void> updateBook(@RequestBody BookUpdateRequest bookUpdateRequest){
        if(bookService.updateBook(bookUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除图书
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Void> deleteBook(@RequestBody DeleteRequest deleteRequest){
        if(bookService.deleteBook(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
    @GetMapping("/get")
    public BaseResponse<BookVO> getBookById(Long id){
        if(id == null || id <= 0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = bookService.getById(id);
        if(book == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        BookVO bookVO = bookService.getBookVO(book);

        return ResultUtils.success(bookVO);
    }
}
