package cn.knight.bookstore.category.service;

import java.util.List;

import cn.knight.bookstore.book.dao.BookDao;
import cn.knight.bookstore.category.admin.web.servlet.CategoryException;
import cn.knight.bookstore.category.dao.CategoryDao;
import cn.knight.bookstore.category.domain.Category;

public class CategoryService {
    private CategoryDao  categoryDao = new CategoryDao();
    private BookDao bookDao = new BookDao();

    /**
     * 查看所有分类
     * @return
     */
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    /**
     * 添加分类
     * @param category
     */
    public void add(Category category) {
        categoryDao.add(category);
    }
    
    /**
     * 根据came查看分类
     * @return
     */
    public Category findByCname(String cname) {
        return categoryDao.findByCname(cname);
    }

    /**
     * 删除分类
     * @param cid
     * @throws CategoryException 
     */
    public void delete(String cid) throws CategoryException {
        // 获取该分类下图书的本书
         int count = bookDao.getCountByCid(cid);
         // 如果还有图书，不让删除，抛出异常
         if(count > 0) throw new CategoryException("该分类下还有图书，不能删除！");
         // 删除该分类
         categoryDao.delete(cid);
    }

    /**
     * 加载分类
     * @param cid
     * @return
     */
    public Category load(String cid) {
        // TODO Auto-generated method stub
        return categoryDao.load(cid);
    }

    /**
     * 修改分类
     * @param category
     */
    public void edit(Category category) {
        categoryDao.edit(category);
    }
}
