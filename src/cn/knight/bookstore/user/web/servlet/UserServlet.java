package cn.knight.bookstore.user.web.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import cn.knight.bookstore.cart.domain.Cart;
import cn.knight.bookstore.user.domain.User;
import cn.knight.bookstore.user.service.UserException;
import cn.knight.bookstore.user.service.UserService;
import cn.knight.utils.CommonUtils;
/**
 * User表述层
 * @author Knight
 *
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    public String quit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.getSession().invalidate();
        return "r:/index.jsp";
    }
    
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException{
	    // 1.封装表单数据
	    User form = CommonUtils.toBean(request.getParameterMap(), User.class);
	    // 2.创建存错误的Map
	    Map<String,String> errors = new HashMap<String,String>();
	    // 3.表单校验
	    String username = form.getUsername();
        if(username == null || username.trim().isEmpty()) {
            errors.put("username", "用户名不能为空！");
        }else if(username.length() < 3 || username.length() > 10) {
            errors.put("username", "用户名长度必须在3~10之间");
        }
        
        String password = form.getPassword();
        if(password == null || password.trim().isEmpty()) {
            errors.put("password", "密码不能为空！");
        }else if(password.length() < 3 || password.length() > 10) {
            errors.put("password", "密码长度必须在3~10之间");
        }
        
        /*
         * 判断是否存在错误信息
         */
        if(errors.size()>0) {
            /*
             * 1.保存错误信息
             * 2.保存表单数据
             * 3.转发到regist.jsp
             */
            request.setAttribute("errors", errors);
            request.setAttribute("form", form);
            return "f:/jsps/user/login.jsp";
        }
        
	    try {
	        User user = userService.login(form);
	        request.getSession().setAttribute("session_user", user);
	        /*
	         * 给用户添加一辆车，在session中
	         */
	        request.getSession().setAttribute("cart", new Cart());
	        return "r:/index.jsp";
	    } catch(UserException e) {
	        request.setAttribute("msg", e.getMessage());
	        request.setAttribute("form", form);
	        return "f:/jsps/user/login.jsp";
	    }

	}
	
	/**
	 * 激活
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String active(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	    /*
	     * 1.获取参数中的code
	     * 2.使用激活码调用service#active(String code)方法
	     *     > 保存错误信息到request,转发到msg.jsp
	     * 3.保存激活信息到request中
	     * 4.转发到msg.jsp
	     */
	    String code = request.getParameter("code");
	    try {
            userService.active(code);
            request.setAttribute("msg", "恭喜您激活成功！请马上登录！");
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
        }
	    
	    return "f:/jsps/msg.jsp";
	}
	
	/**
	 * 注册功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	    /*
	     * 1.封装表单数据到form对象中
	     * 2.补全：uid，code
	     * 3.输入校验
	     * 4.调用service方法完成注册
	     * 5.发邮件
	     * 6.保存成功信息到msg.jsp
	     */
	    //1.
	    User form = CommonUtils.toBean(request.getParameterMap(), User.class);
	    //2.
	    form.setUid(CommonUtils.uuid());
	    form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
	    //3. 创建map装错误信息,key=字段名称，value=错误信息
	    Map<String,String> errors = new HashMap<String,String>();
	    /*
	     * 获取表单数据进行校验
	     */
	    String username = form.getUsername();
	    if(username == null || username.trim().isEmpty()) {
	        errors.put("username", "用户名不能为空！");
	    }else if(username.length() < 3 || username.length() > 10) {
	        errors.put("username", "用户名长度必须在3~10之间");
	    }
	    
	    String password = form.getPassword();
        if(password == null || password.trim().isEmpty()) {
            errors.put("password", "密码不能为空！");
        }else if(password.length() < 3 || password.length() > 10) {
            errors.put("password", "密码长度必须在3~10之间");
        }
	    
        String email = form.getEmail();
        if(email == null || email.trim().isEmpty()) {
            errors.put("email", "Email不能为空！");
        }else if(!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            errors.put("email", "Email格式错误！");
        }
        
        /*
         * 判断是否存在错误信息
         */
        if(errors.size()>0) {
            /*
             * 1.保存错误信息
             * 2.保存表单数据
             * 3.转发到regist.jsp
             */
            request.setAttribute("errors", errors);
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }
        
        //4.调用service方法完成注册
        try {
            userService.regist(form);
            /*
             * 执行到这里说明注册成功，没有抛出异常
             */
        } catch (UserException e) {
            /*
             * 1.保存异常信息
             * 2.保存form
             * 3.转发到regist.jsp
             */
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }
        
        /*
         * 发邮件
         * 准备配置文件
         */
        Properties props = new Properties();
        InputStreamReader isr = new InputStreamReader(this.getClass().getClassLoader()
                .getResourceAsStream("email_template.properties"),"utf-8");
        props.load(isr);
        String host = props.getProperty("host");
        String uname = props.getProperty("uname");
        String pwd = props.getProperty("pwd");
        String from = props.getProperty("from");
        String to = form.getEmail();
        String subject = props.getProperty("subject");
        String content = props.getProperty("content");
        content = MessageFormat.format(content, form.getCode());//替换占位符{0}
        
        Session session = MailUtils.createSession(host, uname, pwd);
        Mail mail = new Mail(from,to,subject,content);
        try {
            MailUtils.send(session, mail);//发邮件
        } catch (MessagingException e) {
            
        }//发邮件
        
        /*
         * 1.保存成功信息
         * 2.转发到regist.jsp
         */
        request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱进行激活~");
        return "f:/jsps/msg.jsp";
	}
}
