package cn.knight.bookstore.category.admin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.knight.bookstore.category.domain.Category;
import cn.knight.bookstore.category.service.CategoryService;
import cn.knight.utils.CommonUtils;

@WebServlet(name="AdminCategoryServlet",urlPatterns="/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private CategoryService categoryService = new CategoryService();   

    /**
     * 查询所有分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /*
	     * 1.调用service方法，得到所有分类，
	     * 2.保存到request域，转发到/adminjsps/admin/category/list.jsp
	     */
	    request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/category/list.jsp";
	}
	
	
	/**
	 * 添加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 封装表单数据
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        // 补全cid
        category.setCid(CommonUtils.uuid());
        // 判断分类是否已经存在，存在转发msg
        if(categoryService.findByCname(category.getCname()) != null) {
            request.setAttribute("msg", "添加的分类已经存在~");
            return "f:/adminjsps/admin/msg.jsp";
        }
        // 添加分类
        categoryService.add(category);
        return findAll(request, response);
    }
    
    /**
     * 删除分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取cid
        String cid = request.getParameter("cid");
        // 调用service#delete(String cid); 如果抛出异常，转发到msg.jsp
        try {
            categoryService.delete(cid);
            return findAll(request, response);
        } catch (CategoryException e ) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/admin/msg.jsp";
        }   
    }
    
    /**
     * 往mod.jsp加载分类名称
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取cid
        String cid = request.getParameter("cid");
        request.setAttribute("category", categoryService.load(cid));
        return "f:/adminjsps/admin/category/mod.jsp";
    }

    
    /**
     * 修改分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 封装表单数据
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        categoryService.edit(category);
        return findAll(request, response);
    }
}
