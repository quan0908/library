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
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordAddRequest;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordQueryRequest;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordUpdateRequest;
import com.lib.model.entity.BookBorrowRecord;
import com.lib.model.vo.BookBorrowRecordVO;
import com.lib.service.BookBorrowRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 借书记录接口
 * @author quan
 */
@RestController
@RequestMapping("/borrowRecord/book")
public class BookBorrowRecordController {
    @Resource
    private BookBorrowRecordService bookBorrowRecordService;

    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Page<BookBorrowRecord>> listBookBorrowRecordByPage(@RequestBody BookBorrowRecordQueryRequest
                                                                                       bookBorrowRecordQueryRequest,
                                                                            HttpServletRequest request) {
        long current = bookBorrowRecordQueryRequest.getCurrent();
        long size = bookBorrowRecordQueryRequest.getPageSize();
        Page<BookBorrowRecord> bookBorrowRecordPage = bookBorrowRecordService.page(new Page<>(current, size),
                bookBorrowRecordService.getQueryWrapper(bookBorrowRecordQueryRequest));
        return ResultUtils.success(bookBorrowRecordPage);
    }

    /**
     * 分页获取借书记录列表
     *
     * @param bookBorrowRecordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<BookBorrowRecordVO>> listBookBorrowRecordVOByPage(@RequestBody BookBorrowRecordQueryRequest
                                                                               bookBorrowRecordQueryRequest,
                                                                   HttpServletRequest request) {
        if (bookBorrowRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = bookBorrowRecordQueryRequest.getCurrent();
        long size = bookBorrowRecordQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<BookBorrowRecord> bookBorrowRecordPage = bookBorrowRecordService.page(new Page<>(current, size),
                bookBorrowRecordService.getQueryWrapper(bookBorrowRecordQueryRequest));
        Page<BookBorrowRecordVO> bookBorrowRecordVOPage = new Page<>(current, size, bookBorrowRecordPage.getTotal());
        List<BookBorrowRecordVO> bookVO = bookBorrowRecordService.getBookBorrowRecordVO(bookBorrowRecordPage.getRecords());
        bookBorrowRecordVOPage.setRecords(bookVO);
        return ResultUtils.success(bookBorrowRecordVOPage);
    }

    /**
     * 添加图书
     * @param bookBorrowRecordAddRequest 借书记录添加请求
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Void> addBookBorrowRecord(@RequestBody BookBorrowRecordAddRequest bookBorrowRecordAddRequest,
                                                  HttpServletRequest request){
        if(bookBorrowRecordService.addBookBorrowRecord(bookBorrowRecordAddRequest,request)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"添加失败");
    }

    /**
     * 修改借书记录(还书 修改isReturn)
     * @param bookBorrowRecordUpdateRequest 借书记录修改请求
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Void> updateBorrowRecord(@RequestBody BookBorrowRecordUpdateRequest bookBorrowRecordUpdateRequest){
        if(bookBorrowRecordService.updateBookBorrowRecord(bookBorrowRecordUpdateRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"修改失败");
    }

    /**
     * 删除借书记录
     * @param deleteRequest 删除请求
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.BOOK_ADMIN)
    public BaseResponse<Void> deleteBorrowRecord(@RequestBody DeleteRequest deleteRequest){
        if(bookBorrowRecordService.deleteBookBorrowRecord(deleteRequest)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"删除失败");
    }
}
