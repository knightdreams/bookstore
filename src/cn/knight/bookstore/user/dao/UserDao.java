package cn.knight.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.knight.bookstore.user.domain.User;
import lib.TxQueryRunner;

/**
 * user持久层
 * @author Knight
 *
 */

public class UserDao {
    private QueryRunner qr = new TxQueryRunner();
    
    
    /**
     * 按username查询user
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        try {
            String sql = "select * from tb_user where username=?";
            return qr.query(sql, new BeanHandler<User>(User.class),username);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * 按email查询user
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        try {
            String sql = "select * from tb_user where email=?";
            return qr.query(sql, new BeanHandler<User>(User.class),email);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 插入User
     * @param user
     */
    public void addUser(User user) {
        try {
            String sql = "insert into tb_user values(?,?,?,?,?,?)";
            Object[] params = {user.getUid(),user.getUsername(),
                    user.getPassword(),user.getEmail(),user.getCode(),
                    user.isState()};
            qr.update(sql,params);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 按激活码查User
     * @param code
     * @return
     */
    public User findByCode(String code) {
        try {
            String sql = "select * from tb_user where code=?";
            return qr.query(sql, new BeanHandler<User>(User.class),code);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void updateState(String uid,boolean state) {
        String sql = "update tb_user set state=? where uid=?";
        try {
            qr.update(sql,state,uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
