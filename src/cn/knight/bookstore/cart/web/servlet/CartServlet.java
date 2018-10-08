package cn.knight.bookstore.cart.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.knight.bookstore.book.domain.Book;
import cn.knight.bookstore.book.service.BookService;
import cn.knight.bookstore.cart.domain.Cart;
import cn.knight.bookstore.cart.domain.CartItem;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet(name="CartServlet",urlPatterns="/CartServlet")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * 添加购物条目
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /*
	     * 1.获取车
	     * 2.得到条目（得到图书和数量）
	     * 
	     */
	    Cart cart = (Cart)request.getSession().getAttribute("cart");
	    /*
	     * 得到条目，得到图书和数量
	     * 先得到bid，然后查询数据库得到book
	     * 数量表单中有
	     */
	    String bid = request.getParameter("bid");
	    Book book = new BookService().findByBid(bid);
	    int count = Integer.parseInt(request.getParameter("count"));
	    CartItem cartItem = new CartItem();
	    cartItem.setBook(book);
	    cartItem.setCount(count);
	    /*
	     * 把条目添加到车中
	     */
	    cart.add(cartItem);
	    return "f:/jsps/cart/list.jsp";
	}
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 1.得到车
         * 2.车.clear
         */
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        cart.clear();
        return "f:/jsps/cart/list.jsp";
    }
    
    /**
     * 删除购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 1.得到车
         * 2.得到bid
         */
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        String bid = request.getParameter("bid");
        cart.remove(bid);
        return "f:/jsps/cart/list.jsp";
    }
}
