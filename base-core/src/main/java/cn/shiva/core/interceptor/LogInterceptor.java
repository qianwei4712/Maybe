package cn.shiva.core.interceptor;

import cn.shiva.core.utils.LogUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shiva   2019/12/24 19:53
 */
@Configuration
public class LogInterceptor implements HandlerInterceptor {

    /**
     * 整个请求完成之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LogUtils.logging(request, handler, ex);
    }
}
