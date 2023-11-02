package com.lib.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.lib.model.entity.Book;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书VO
 * @author quan
 */
@Data
public class BookVO implements Serializable {
    /**
     * 图书id
     */
    private Long id;

    /**
     * 图书名
     */
    private String bookName;

    /**
     * 图书数量
     */
    private Integer bookNumber;

    /**
     * 图书分类
     */
    private String type;

    /**
     * 图书位置
     */
    private String bookLocation;

    /**
     * 图书作者
     */
    private String bookAuthor;


    /**
     * 图书简介
     */
    private String bookTra;

    /**
     * 图书封面
     */
    private String bookCover;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param bookVO
     * @return
     */
    public static Book voToObj(BookVO bookVO) {
        if (bookVO == null) {
            return null;
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookVO, book);
        
        return book;
    }

    /**
     * 对象转包装类
     *
     * @param book
     * @return
     */
    public static BookVO objToVo(Book book) {
        if (book == null) {
            return null;
        }
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(book, bookVO);
        return bookVO;
    }

}
