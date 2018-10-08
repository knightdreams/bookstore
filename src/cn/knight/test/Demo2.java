package cn.knight.test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import lib.TxQueryRunner;

public class Demo2 {
    
}


class BaseDao<T>{
    private QueryRunner qr = new TxQueryRunner();
    private Class<T> beanClass;
    
    @SuppressWarnings("unchecked")
    public BaseDao() {
        beanClass = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).
                getActualTypeArguments()[0];
    }
    
    /**
     * 添加
     * @param bean
     * @throws SQLException
     */
    public void add(T bean) throws SQLException {
        Field[] fs = beanClass.getDeclaredFields();
        
        String sql = "insert into "+ beanClass.getSimpleName() +" values(";
        for (int i = 0; i < fs.length; i++) {
            sql += "?";
            if(i < fs.length-1) {
                sql += ",";
            }
        }
        sql += ")";
        
        Object[] params = {};
        qr.update(sql,params);
    }
    
    public void update(T bean) {
        
    }
    
    public void delete(String uuid) {
        
    }
    
    public T load(String uuid) {
        return null;
    }
    
    public List<T> findAll(){
        return null;
    }
}