package cn.knight.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.knight.bookstore.book.domain.Book;
import cn.knight.bookstore.category.domain.Category;
import cn.knight.utils.CommonUtils;

public class BookDao {
    private QueryRunner qr = new TxQueryRunner();
    
    /**
     * 查询所有图书
     * @return
     */
    public List<Book> findAll(){
        String sql = "select * from book where del=false";
        try {
            return qr.query(sql, new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 根据分类查找图书
     * @param cid
     * @return
     */
    public List<Book> findByCid(String cid) {
        String sql = "select * from book where cid=? and del=false";
        try {
            return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 根据Bid加载图书
     * @param bid
     * @return
     */
    public Book findByBid(String bid) {
        String sql = "select * from book where bid=?";
        try {
            Map<String,Object> map =  qr.query(sql, new MapHandler(), bid);
            Category category = CommonUtils.toBean(map, Category.class);
            Book book = CommonUtils.toBean(map, Book.class);
            book.setCategory(category);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询指定分类下的图书本书
     * @param cid 
     * @return
     */
    public int getCountByCid(String cid) {
        String sql = "select count(*) from book where cid=?";
        try {
            Number number = (Number) qr.query(sql, new ScalarHandler<Object>(), cid);
            return number.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加图书
     * @param book
     */
    public void add(Book book) {
        String sql = "insert into book values(?,?,?,?,?,?)";
        try {
            Object[] params = {book.getBid(),book.getBname(),book.getPrice()
                    ,book.getAuthor(),book.getImage(),book.getCategory().getCid()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除
     * @param bid
     */
    public void del(String bid) {
        String sql = "update book set del=true where bid=?";
        try {
            qr.update(sql, bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(Book book) {
        String sql = "update book set bname=?,price=?,author=?,image=?,cid=? where bid=?";
        try {
            Object[] params = {book.getBname(),book.getPrice()
                    ,book.getAuthor(),book.getImage(),book.getCategory().getCid(),book.getBid()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
