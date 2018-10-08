package cn.knight.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.junit.Test;

public class PropertiesTest {

    @Test
    public void fun1(){
        Properties props = new Properties();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(this.getClass().getClassLoader()
                    .getResourceAsStream("email_template.properties"),"utf-8");
            props.load(isr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String subject = props.getProperty("subject");
        String content = props.getProperty("content");
        
        System.out.println(subject+content);
    }
}
