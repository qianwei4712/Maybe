package cn.gov.gsport.core.utils;

import cn.gov.gsport.common.lang.ExceptionUtils;
import cn.gov.gsport.common.lang.StringUtils;
import cn.gov.gsport.common.network.IpUtils;
import cn.gov.gsport.core.constant.BaseConstant;
import cn.gov.gsport.system.entity.Log;
import cn.gov.gsport.system.entity.User;
import cn.gov.gsport.system.mapper.LogMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @author shiva   2019/12/24 11:58
 */
public class LogUtils {

    private static LogMapper logMapper = SpringContextHolder.getBean(LogMapper.class);

    private static final String USER_AGENT = "user-agent";


    /**
     * 登陆日志
     */
    public static void logging(HttpServletRequest request, Integer logType){
        logging(request, logType, null, null);
    }

    /**
     * 操作日志、异常日志
     */
    public static void logging(HttpServletRequest request, Object handler, Exception ex){
        logging(request, null, handler,  ex);
    }

    /**
     * 日志记录
     * @param logType 日志类型 Log.常量，0-登陆日志，1-操作日志，2-异常日志
     * @param ex 异常
     */
    public static void logging(HttpServletRequest request, Integer logType, Object handler, Exception ex){
        User user = SysUtils.getUser();
        if (user.getId() != null && request != null){
            Log log = new Log();
            log.setLogType(logType);
            log.setCreateDate(new Date());
            log.setUserid(user.getId());
            log.setUsername(user.getName());
            log.setUserAgent(request.getHeader(USER_AGENT));
            if (Log.LOGIN_LOG.equals(logType)){
                //登陆日志添加ip
                log.setRemoteAddr(IpUtils.getRemoteAddr(request));
                // 保存日志信息
                logMapper.insert(log);
            }else {
                //错误日志和操作日志添加请求参数
                log.setRequestUrl(request.getRequestURI());
                log.setParams(getParams(request.getParameterMap()));
                new SaveLogThread(log, handler, ex).start();
            }
        }
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread{

        private Log log;
        private Object handler;
        private Exception ex;

        public SaveLogThread(Log log, Object handler, Exception ex){
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.ex = ex;
        }

        @Override
        public void run() {
            //不是登陆日志，需要添加其他信息，标题
            String titleSuffix = null;
            if (handler instanceof HandlerMethod){
                Method m = ((HandlerMethod)handler).getMethod();
                RequestMapping rm = m.getAnnotation(RequestMapping.class);
                titleSuffix = rm.name();
            }
            // 如果有异常，设置异常信息
            log.setExceptions(ExceptionUtils.getStackTraceAsString(ex));
            // 如果无标题并无异常日志，则不保存信息
            if (StringUtils.isBlank(titleSuffix) && StringUtils.isBlank(log.getExceptions())){
                return;
            }else if (StringUtils.isNotBlank(log.getExceptions())){
                log.setLogType(Log.ERROR_LOG);
            }else {
                log.setLogType(Log.OPERATE_LOG);
            }
            //设置标题
            log.setTitle(getMenuNamePath(log.getRequestUrl(), titleSuffix));
            // 保存日志信息
            logMapper.insert(log);
        }
    }

    /**
     * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
     */
    public static String getMenuNamePath(String requestUri, String titleSuffix){
        //获取菜单MAP<href, 菜单名称拼接>
        Map<String, String> menuMap = SysUtils.getMenuMap();
        String menuNamePath = menuMap.get(requestUri);
        //菜单控制器下属子方法，根据‘/’循环切割获取菜单拼接
        while (StringUtils.isBlank(menuNamePath)){
            if (requestUri.length() < 2){
                break;
            }
            requestUri = requestUri.substring(0, requestUri.lastIndexOf(BaseConstant.URL_SYMBOL));
            menuNamePath = menuMap.get(requestUri);
        }
        //加入最后的结尾
        if (!StringUtils.isBlank(titleSuffix)){
            menuNamePath = menuNamePath + BaseConstant.SPLIT_SYMBOL_DASH + titleSuffix;
        }
        return menuNamePath;
    }

    private static String getParams(Map<String, String[]> paramMap){
        if (paramMap == null){
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
            params.append("".equals(params.toString()) ? "" : "&").append(param.getKey()).append("=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        return params.toString();
    }

}
