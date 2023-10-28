package com.lib.model.vo;

import com.lib.model.entity.Book;
import com.lib.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author quan
 */
@Data
public class BookVO implements Serializable {
    /**
     * 图书id
     */
    private Long id;

    /**
     * 图书名字
     */
    private String bookName;

    /**
     * 图书数量
     */
    private Integer bookNumber;

    /**
     * 图书类型
     */
    private String type;

    /**
     * 图书位置
     */
    private String bookLocation;

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
