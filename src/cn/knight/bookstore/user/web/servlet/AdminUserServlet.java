package cn.knight.bookstore.user.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/admin/AdminUserServlet")
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    String username = request.getParameter("adminname");
	    String password = request.getParameter("adminpassword");
	    
	    if(!(username.equals("admin")&&password.equals("admin"))) {
	        request.setAttribute("msg", "用户名密码错误~");
	        request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);
	    } else {
	        request.getSession().setAttribute("adminUser", "admin");
	        response.sendRedirect("/bookstore/adminjsps/admin/index.jsp");
	    }
	}

}
