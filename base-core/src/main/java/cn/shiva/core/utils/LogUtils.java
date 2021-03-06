package cn.shiva.core.utils;

import cn.shiva.common.lang.ExceptionUtils;
import cn.shiva.common.lang.StringUtils;
import cn.shiva.common.network.IpUtils;
import cn.shiva.core.constant.BaseConstant;
import cn.shiva.system.entity.Log;
import cn.shiva.system.entity.User;
import cn.shiva.system.mapper.LogMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shiva   2019/12/24 11:58
 */
public class LogUtils {

    private static LogMapper logMapper = SpringContextHolder.getBean(LogMapper.class);

    private static final String USER_AGENT = "user-agent";

    public static ExecutorService pool = new ThreadPoolExecutor(
                                            10, 10, 0L,
                                            TimeUnit.MILLISECONDS,
                                            new LinkedBlockingQueue<>(),
                                            new LoggingThreadFactory());

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
     * controller异常捕捉
     */
    public static void exceptionCatch(HttpServletRequest request, Exception ex){
        User user = SysUtils.getUser();
        if (user.getId() != null && request != null){
            Log log = new Log();
            log.setLogType(Log.ERROR_LOG);
            log.setCreateDate(new Date());
            log.setUserid(user.getId());
            log.setUsername(user.getName());
            log.setUserAgent(request.getHeader(USER_AGENT));
            //错误日志和操作日志添加请求参数
            log.setRequestUrl(request.getRequestURI());
            log.setParams(getParams(request.getParameterMap()));
            // 使用线程池插入日志
            pool.execute(() -> {
                log.setExceptions(ExceptionUtils.getStackTraceAsString(ex));
                log.setTitle(getMenuNamePath(log.getRequestUrl(), null));
                // 保存日志信息
                logMapper.insert(log);
            });
        }
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
                // 将开启线程改为线程池方式
                pool.execute(() -> {
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
                });
            }
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

    /**
     * 获得request参数
     */
    private static String getParams(Map<String, String[]> paramMap){
        if (paramMap == null){
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()){
            params.append("".equals(params.toString()) ? "" : "&").append(param.getKey()).append("=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        return params.toString();
    }

    /**
     * 自定义日志线程工厂
     */
    static class LoggingThreadFactory implements java.util.concurrent.ThreadFactory{

        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        LoggingThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-logging-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
