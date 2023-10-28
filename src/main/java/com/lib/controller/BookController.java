package com.lib.controller;

import com.lib.common.BaseResponse;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.common.ResultUtils;
import com.lib.model.dto.book.BookAddRequest;
import com.lib.model.dto.book.BookUpdateRequest;
import com.lib.model.vo.BookVO;
import com.lib.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @GetMapping("/get/books")
    public BaseResponse<List<BookVO>> getBooks(){
        return ResultUtils.success(bookService.getBooks());
    }

    /**
     * 添加图书
     * @param bookAddRequest 图书添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addBook(@RequestBody BookAddRequest bookAddRequest){
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
    public BaseResponse<Void> deleteBook(@RequestBody DeleteRequest deleteRequest){
        if(bookService.deleteBook(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
}
