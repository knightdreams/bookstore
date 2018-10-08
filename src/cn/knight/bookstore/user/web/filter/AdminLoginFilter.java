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

/**
 * Servlet Filter implementation class AdminLoginFilter
 */
@WebFilter(
        urlPatterns = {
                "/adminjsps/admin/*"
        },
        servletNames = {
                "AdminBookServlet",
                "AdminAddBookServlet",
                "AdminCategoryServlet"
        })
public class AdminLoginFilter implements Filter {

    public AdminLoginFilter() {
    }

	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    String adminUser = (String) httpServletRequest.getSession().getAttribute("adminUser");
	    if(adminUser!=null) {
	        chain.doFilter(request, response);
	    } else {
           httpServletRequest.setAttribute("msg", "您还没有登录！");
           httpServletRequest.getRequestDispatcher("/adminjsps/msg.jsp")
                .forward(httpServletRequest, response);
	    }
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
