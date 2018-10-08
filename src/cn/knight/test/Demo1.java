package cn.knight.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;

import org.junit.Test;

public class Demo1 {
    @Test
    public void fun1() {
       /*
        * DateFormat    SimpleDateFormat
        * NumberFormat.getInstance
        */
        /*
         * 包含了占位符的字符串就是模板
         * 可变参数，需要指定模板中的占位符的值！有几个占位符就得提供几个参数
         */
        String s = MessageFormat.format("{0}或{1}错误", "username","password");
        System.out.println(s);
    }
    
    @Test
    public void fun2() {
        System.out.println(2.0-1.1);
    }
    
    /**
     * 100的阶乘 用BigInteger包装数据
     */
    @Test
    public void fun3() {
        BigInteger sum = BigInteger.valueOf(1);
        for (int i = 1; i <= 100; i++) {
            BigInteger bi = BigInteger.valueOf(i);
            sum = sum.multiply(bi);
        }
        System.out.println(sum);
    }
    
    /**
     * BigDecimal
     * 可以出来二进制运算导致的误差
     */
    @Test
    public void fun4() {
        /*
         * 1.创建BigDecimal对象时，必须使用String构造器！
         */
        BigDecimal d1 = new BigDecimal("2.0");
        BigDecimal d2 = new BigDecimal("1.1");
        BigDecimal d3 = d1.subtract(d2);
        
        System.out.println(d3);
    }
}
