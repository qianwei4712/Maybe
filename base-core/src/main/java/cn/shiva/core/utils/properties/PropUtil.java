package cn.shiva.core.utils.properties;

import java.util.Properties;

/**
 * 全局配置读取，只读取maybe-web模块yml
 * @author shiva   2020/1/2 19:21
 */
public class PropUtil {

    private static Properties ymlProperties = new Properties();

    public PropUtil(Properties properties){
        ymlProperties = properties;
    }

    public static String get(String key){
        return ymlProperties.getProperty(key);
    }

}
