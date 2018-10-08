package cn.knight.bookstore.cart.domain;

import java.math.BigDecimal;

import cn.knight.bookstore.book.domain.Book;

public class CartItem {
    private Book book;// 商品
    private int count;// 数量
    
    /**
     * 小计方法
     * @return
     * 处理了二进制误差问题
     */
    public double getSubtotal() {
        BigDecimal d1 = new BigDecimal(book.getPrice()+"");
        BigDecimal d2 = new BigDecimal(count+"");
        return d1.multiply(d2).doubleValue();
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    @Override
    public String toString() {
        return "CartItem [book=" + book + ", count=" + count + "]";
    }
    
}
