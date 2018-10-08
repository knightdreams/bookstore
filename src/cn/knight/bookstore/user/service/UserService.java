package cn.knight.bookstore.user.service;

import cn.knight.bookstore.user.dao.UserDao;
import cn.knight.bookstore.user.domain.User;

/**
 * User业务层
 * @author Knight
 *
 */
public class UserService {
    private UserDao userDao = new UserDao();
    
    /**
     * 注册
     * @param form
     * @throws UserException
     */
    public void regist(User form) throws UserException {
        User user = userDao.findByUsername(form.getUsername());
        // 校验用户名
        if(user != null) throw new UserException("用户名已被注册！");
        
        // 校验email
        user = userDao.findByEmail(form.getEmail());
        if(user != null) throw new UserException("Email已被注册！");
        
        // 插入用户到数据库
        userDao.addUser(form);
    }
    
    /**
     * 激活
     * @param code
     * @throws UserException
     */
    public void active(String code) throws UserException{
        User user = userDao.findByCode(code);
        
        if(user == null) throw new UserException("激活码无效！");
        
        if(user.isState()) throw new UserException("您已经激活，不要再激活了！");
        
        userDao.updateState(user.getUid(), true);
    }
    
    /**
     * 登录
     * @param username
     * @return
     * @throws UserException
     */
    public User login(User form) throws UserException{
        User user = userDao.findByUsername(form.getUsername());
        
        if(user == null) throw new UserException("用户名不存在!");
        
        if(!form.getPassword().equals(user.getPassword())) throw new UserException("密码错误！");
        
        if(!user.isState()) throw new UserException("您还未激活");
        
        return user;
    }
}
