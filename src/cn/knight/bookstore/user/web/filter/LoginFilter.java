package cn.knight.bookstore.user.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cn.knight.bookstore.user.domain.User;


@WebFilter(
		urlPatterns = { 
				"/jsps/cart/*", 
				"/jsps/order/*"
		}, 
		servletNames = { 
				"CartServlet", 
				"OrderServlet"
		})
public class LoginFilter implements Filter {

	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    /*
	     * 1.从session中获取用户信息
	     * 2.判断如果session存在用户信息，放行
	     * 3.否则，保存错误信息，转发到login.jsp
	     */
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    User user = (User) httpServletRequest.getSession().getAttribute("session_user");
	    if(user != null) {
	        chain.doFilter(request, response);
	    } else {
	        httpServletRequest.setAttribute("msg", "您还没有登录！");
	        httpServletRequest.getRequestDispatcher("/jsps/user/login.jsp")
	            .forward(httpServletRequest, response);
	    }
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
