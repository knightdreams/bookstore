package cn.knight.bookstore.book.admin.web.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.knight.bookstore.book.domain.Book;
import cn.knight.bookstore.book.service.BookService;
import cn.knight.bookstore.category.domain.Category;
import cn.knight.bookstore.category.service.CategoryService;
import cn.knight.utils.CommonUtils;

/**
 * 添加图书。上传
 * @author Knight
 *
 */
@WebServlet(name="AdminAddBookServlet",urlPatterns="/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		/*
		 * 1.表单数据封装到Book对象中
		 * 上传三步
		 */
		// 创建工厂
		DiskFileItemFactory factory = new DiskFileItemFactory(15*1024,new File("C:/temp"));
		// 创建解析器
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 设置单个文件大小为20KB
		sfu.setFileSizeMax(20*1024);
		// 使用解析器解析request对象，得到List<FileItem>
		try {
            List<FileItem> fileItems = sfu.parseRequest(request);
            /*
             * 把fileItems中数据封装到Book对象中
             *  > 把普通表单数据先封装到Map中
             *  > 再把map中数据封装到Book对象中
             */
            Map<String,String> map = new HashMap<String,String>();
            for (FileItem fileItem : fileItems) {
                if(fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
                }
            }
            Book book = CommonUtils.toBean(map, Book.class); // 还差个图片路径
            /*
             * 需要把Map中的cid封装到category对象中，再把category赋值给Book
             * 补全bid
             */
            Category category = CommonUtils.toBean(map, Category.class);
            book.setCategory(category);
            book.setBid(CommonUtils.uuid());
            
            /*
             * 2.保存上传的文件
             *  > 保存的路径（目录）
             *  > 保存的文件名称
             */
            // 得到保存目录
            String savepath = this.getServletContext().getRealPath("/book_img");
            // 得到文件名 加uuid避免文件冲突
            String filename = CommonUtils.uuid()+"_"+fileItems.get(1).getName();
            
            /*
             * 校验文件的扩展名
             */
            if(!filename.toLowerCase().endsWith("jpg")) {
                request.setAttribute("msg", "您上传的图片扩展名不是JPG");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                    .forward(request, response);
                return;
            }
            
            // 使用目录和名称创建目标文件
            File destFile = new File(savepath, filename);
            // 保存上传文件到目标文件位置
            fileItems.get(1).write(destFile);
            
            /*
             * 3.补全book对象image
             */
            book.setImage("book_img/"+filename);
            

            
            /*
             * 4.使用bookService完成保存
             */
            bookService.add(book);
            
            /*
             * 校验图片的尺寸
             */
            Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
            if(image.getWidth(null) > 200 || image.getHeight(null) > 200) {
                destFile.delete();
                request.setAttribute("msg", "您上传的图片尺寸超过了200*200！");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                    .forward(request, response);
                return;
            }
            
            /*
             * 5.返回AdminBookServlet#findAll
             */
            request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll")
                .forward(request, response);
        } catch (Exception e) {
            if(e instanceof FileUploadBase.FileSizeLimitExceededException) {
                request.setAttribute("msg", "您上传的文件超出了20KB");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                    .forward(request, response);
            }
        }
	}

}
