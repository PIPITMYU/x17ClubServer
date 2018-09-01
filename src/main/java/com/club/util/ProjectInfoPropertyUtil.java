package com.club.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 
 * 项目信息文件获取工具�?
 * @author sbb
 * 
 */
public class ProjectInfoPropertyUtil {
    private static Properties props;
    static{
        loadProps();
    }
    
    // 单例模式实现初始化加载properties文件
    private synchronized static void loadProps(){
   
        props = new Properties();
        InputStream in = null;
        try {
        	//第一种，通过类加载器进行获取properties文件�?
        	 in = ProjectInfoPropertyUtil.class.getClassLoader().getResourceAsStream("projectInfo.properties");
            //第二种，通过类进行获取properties文件�?
        	//in = ProjectInfoPropertyUtil.class.getResourceAsStream("/projectInfo.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
          
        } catch (IOException e) {
           
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                
            }
        }
      

    }
    // 获取配置中的属�??
    public static String getProperty(String key){
    	// 单例配置实例未被创建，将重新创建实例
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }
    // 获取配置中的属�??,如果没有找到将给该�?�赋予默认�??
    public static String getProperty(String key, String defaultValue) {
    	// 单例配置实例未被创建，将重新创建实例
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
    
    // test 读取配置方法
    public static void main(String[] args) {
    	String version = ProjectInfoPropertyUtil.getProperty("project_version");
    	System.out.println(version);
    }
}