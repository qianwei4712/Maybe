package cn.shiva.common.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author shiva   2019/12/24 15:36
 */
public class Servelets {

    /**
     * 获取当前请求对象
     */
    public static HttpServletRequest getRequest(){
        try{
            return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        }catch(Exception e){
            return null;
        }
    }

}
