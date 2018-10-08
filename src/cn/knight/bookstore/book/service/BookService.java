package cn.knight.bookstore.book.service;

import java.util.List;

import cn.knight.bookstore.book.dao.BookDao;
import cn.knight.bookstore.book.domain.Book;

public class BookService {
    private BookDao bookDao = new BookDao();

    /**
     * 查询所有图书
     * @return
     */
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    /**
     * 按分类查询图书
     * @return
     */
    public List<Book> findByCid(String cid) {
        return bookDao.findByCid(cid);
    }

    /**
     * 按id查询图书
     * @param bid
     * @return
     */
    public Book findByBid(String bid) {
        return bookDao.findByBid(bid);
    }

    /**
     * 添加图书
     * @param book
     */
    public void add(Book book) {
        bookDao.add(book);
    }

    /**
     * 删除图书
     * @param bid
     */
    public void del(String bid) {
        bookDao.del(bid);
    }

    /**
     * 修改
     * @param book
     */
    public void edit(Book book) {
        bookDao.edit(book);
    }
}
