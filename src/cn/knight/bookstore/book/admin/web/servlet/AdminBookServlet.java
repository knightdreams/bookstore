package cn.knight.bookstore.book.admin.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.knight.bookstore.book.domain.Book;
import cn.knight.bookstore.book.service.BookService;
import cn.knight.bookstore.category.domain.Category;
import cn.knight.bookstore.category.service.CategoryService;
import cn.knight.utils.CommonUtils;

/**
 * Servlet implementation class AdminBookServlet
 */
@WebServlet(name="AdminBookServlet",urlPatterns="/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();
	
	/**
	 * 查询所有图书
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setAttribute("bookList", bookService.findAll());;
	    return "f:/adminjsps/admin/book/list.jsp";
	}
	
	/**
	 * 按bid查询图书
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /*
	     * 1.获取参数bid
	     * 2.获取所有分类，保存到requestScope
	     * 2.根据bid查询该图书
	     * 3.转发到desc.jsp
	     */
	    String bid = request.getParameter("bid");
        request.setAttribute("book", bookService.findByBid(bid));
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/book/desc.jsp";
    }
	
	/**
	 * 添加前操作
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 查询所有分类，保存到requestScope
         */
        request.setAttribute("categoryList",categoryService.findAll() );
        return "f:/adminjsps/admin/book/add.jsp";
    }
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String bid = request.getParameter("bid");
	    bookService.del(bid);
	    return findAll(request, response);
    }
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /*
	     * 1.封装表单数据（要求页面必须把image传过来）
	     * 2.service#edit
	     * 3.返回findAll
	     */
	    Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
	    Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
	    book.setCategory(category);
	    
	    bookService.edit(book);
	    return findAll(request, response);
    }
}
