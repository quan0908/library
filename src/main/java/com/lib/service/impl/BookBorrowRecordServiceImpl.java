package com.lib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.DeleteRequest;
import com.lib.common.ErrorCode;
import com.lib.constant.BookConstant;
import com.lib.constant.CommonConstant;
import com.lib.exception.BusinessException;
import com.lib.model.dto.book.BookBorrowRequest;
import com.lib.model.dto.book.BookReturnRequest;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordAddRequest;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordQueryRequest;
import com.lib.model.dto.bookBorrowRecord.BookBorrowRecordUpdateRequest;
import com.lib.model.entity.Book;
import com.lib.model.entity.BookBorrowRecord;
import com.lib.model.entity.User;
import com.lib.model.vo.BookBorrowRecordVO;
import com.lib.model.vo.BookVO;
import com.lib.model.vo.UserVO;
import com.lib.service.BookBorrowRecordService;
import com.lib.mapper.BookBorrowRecordMapper;
import com.lib.service.BookService;
import com.lib.service.UserService;
import com.lib.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author vv
*/
@Service
public class BookBorrowRecordServiceImpl extends ServiceImpl<BookBorrowRecordMapper, BookBorrowRecord>
    implements BookBorrowRecordService{
    @Resource
    private UserService userService;

    @Resource
    private BookService bookService;

    /**
     * 获取查询条件
     * @param bookBorrowRecordQueryRequest 借书查询记录请求
     * @return
     */
    @Override
    public QueryWrapper<BookBorrowRecord> getQueryWrapper(BookBorrowRecordQueryRequest bookBorrowRecordQueryRequest) {
        if (bookBorrowRecordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long bookId = bookBorrowRecordQueryRequest.getBookId();
        Long userId = bookBorrowRecordQueryRequest.getUserId();
        String sortField = bookBorrowRecordQueryRequest.getSortField();
        String sortOrder = bookBorrowRecordQueryRequest.getSortOrder();

        QueryWrapper<BookBorrowRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(bookId != null, "bookId", bookId);
        queryWrapper.like(userId != null, "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取全部的借书记录VO
     * @param bookBorrowRecordList
     * @return
     */
    @Override
    public List<BookBorrowRecordVO> getBookBorrowRecordVO(List<BookBorrowRecord> bookBorrowRecordList) {
        if (CollectionUtils.isEmpty(bookBorrowRecordList)) {
            return new ArrayList<>();
        }
        return bookBorrowRecordList.stream().map(this::getBookBorrowRecordVO).collect(Collectors.toList());
    }

    /**
     * 获取借书记录VO
     * @param bookBorrowRecord
     * @return
     */
    @Override
    public BookBorrowRecordVO getBookBorrowRecordVO(BookBorrowRecord bookBorrowRecord) {
        if(bookBorrowRecord == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //获取bookVO
        Long bookId = bookBorrowRecord.getBookId();
        Book book = bookService.getById(bookId);
        if(book == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        BookVO bookVO = BookVO.objToVo(book);

        //获取userVO
        Long userId = bookBorrowRecord.getUserId();
        User user = userService.getById(userId);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = UserVO.objToVo(user);

        BookBorrowRecordVO bookBorrowRecordVO = new BookBorrowRecordVO();
        bookBorrowRecordVO.setBookVO(bookVO);
        bookBorrowRecordVO.setUserVO(userVO);
        bookBorrowRecordVO.setBorrowDays(bookBorrowRecord.getBorrowDays());
        bookBorrowRecordVO.setId(bookBorrowRecord.getId());
        bookBorrowRecordVO.setCreateTime(bookBorrowRecord.getCreateTime());
        return bookBorrowRecordVO;
    }

    /**
     * 添加借书记录
     * @param bookBorrowRecordAddRequest 添加借书记录请求
     * @return
     */
    @Override
    public boolean addBookBorrowRecord(BookBorrowRecordAddRequest bookBorrowRecordAddRequest, HttpServletRequest request) {
        //参数校验
        if(bookBorrowRecordAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long bookId = bookBorrowRecordAddRequest.getBookId();
        Integer borrowDays = bookBorrowRecordAddRequest.getBorrowDays();
        //获取当前id
        User user = userService.getLoginUser(request);

        //判断这本书在数据库中是否存在
        if(bookService.getById(bookId) == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //借书天数小于0
        if(borrowDays < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //加入数据库
        BookBorrowRecord bookBorrowRecord = new BookBorrowRecord();
        bookBorrowRecord.setBookId(bookId);
        bookBorrowRecord.setBorrowDays(borrowDays);
        bookBorrowRecord.setUserId(user.getId());
        return this.save(bookBorrowRecord);
    }

    /**
     * 修改图书记录(还书 只修改了isReturn字段)
     * @param bookUpdateRequest 图书修改请求
     * @return
     */
    @Override
    public boolean updateBookBorrowRecord(BookBorrowRecordUpdateRequest bookUpdateRequest) {
        if(bookUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long bookBorrowRecordId = bookUpdateRequest.getBookBorrowRecordId();
        if(bookBorrowRecordId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        BookBorrowRecord bookBorrowRecord = this.getById(bookBorrowRecordId);
        bookBorrowRecord.setIsReturned(BookConstant.RETURN);
        //todo 借书还要将book的数量-1
        return this.updateById(bookBorrowRecord);
    }


    /**
     * 删除借书记录
     * @param deleteRequest 删除请求
     * @return
     */
    @Override
    public boolean deleteBookBorrowRecord(DeleteRequest deleteRequest) {
        if(deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long bookId = deleteRequest.getId();
        return this.removeById(bookId);
    }

    /**
     * 借书
     * @param bookBorrowRequest 借书请求参数
     * @return
     */
    @Override
    public boolean borrowBook(BookBorrowRequest bookBorrowRequest, HttpServletRequest request) {
        //参数校验
        if(bookBorrowRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long bookId = bookBorrowRequest.getBookId();
        String idCard = bookBorrowRequest.getIdCard();
        String username = bookBorrowRequest.getUsername();
        Integer borrowDays = bookBorrowRequest.getBorrowDays();
        if(bookId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(borrowDays == null || borrowDays <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(StringUtils.isAnyBlank(idCard,username)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //对用户进行校验
        User user = userService.getLoginUser(request);
        if(!user.getIdCard().equals(idCard)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(!user.getUsername().equals(username)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //判断用户以前借这本书是否归还，未归还则不允许用户借
        Long userId = user.getId();
        QueryWrapper<BookBorrowRecord> bookBorrowRecordQueryWrapper = new QueryWrapper<>();
        bookBorrowRecordQueryWrapper.eq("bookId",bookId);
        bookBorrowRecordQueryWrapper.eq("userId",userId);
        bookBorrowRecordQueryWrapper.eq("isReturned",BookConstant.NOT_RETURN);
        BookBorrowRecord bookBorrowRecord = this.getOne(bookBorrowRecordQueryWrapper);
        if(bookBorrowRecord != null){
            throw new BusinessException(ErrorCode.BOOK_BORROW_ERROR);
        }

        //判断书的数量是否足够
        Book book = bookService.getById(bookId);
        if(book == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Integer bookRemaining = book.getBookRemaining();
        //不够的话，不允许借用
        if(bookRemaining <= 0){
            throw new BusinessException(ErrorCode.BOOK_BORROW_TOTAL_ERROR);
        }
        //图书数量足够允许调用
        book.setBookRemaining(bookRemaining - 1);
        if(!bookService.updateById(book)){
            return false;
        }
        //写入借书记录表中
        BookBorrowRecordAddRequest bookBorrowRecordAddRequest = new BookBorrowRecordAddRequest();
        bookBorrowRecordAddRequest.setBookId(bookId);
        bookBorrowRecordAddRequest.setBorrowDays(borrowDays);
        return this.addBookBorrowRecord(bookBorrowRecordAddRequest,request);
    }

    /**
     * 还书
     * @param bookReturnRequest 还书请求
     * @return
     */
    @Override
    public boolean returnBook(BookReturnRequest bookReturnRequest) {
        //参数校验
        if(bookReturnRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long borrowBookRecordId = bookReturnRequest.getBorrowBookRecordId();
        if(borrowBookRecordId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //修改用户借书记录状态
        BookBorrowRecord bookBorrowRecord = this.getById(borrowBookRecordId);
        if(bookBorrowRecord == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        bookBorrowRecord.setIsReturned(BookConstant.RETURN);
        this.updateById(bookBorrowRecord);

        //修改图书表中可借书的数量
        Long bookId = bookBorrowRecord.getBookId();
        Book book = bookService.getById(bookId);
        if(book == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        book.setBookRemaining(book.getBookRemaining() + 1);
        return bookService.updateById(book);
    }

}




