package cn.gov.gsport.core.utils;

import cn.gov.gsport.common.lang.ExceptionUtils;
import cn.gov.gsport.common.lang.StringUtils;
import cn.gov.gsport.common.network.IpUtils;
import cn.gov.gsport.system.entity.Log;
import cn.gov.gsport.system.entity.User;
import cn.gov.gsport.system.mapper.LogMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author shiva   2019/12/24 11:58
 */
public class LogUtils {

    private static LogMapper logMapper = SpringContextHolder.getBean(LogMapper.class);

    private static final String USER_AGENT = "user-agent";

    /**
     * 保存日志
     */
    public static void logging(HttpServletRequest request, Integer logType){
        logging(request, logType, null);
    }

    /**
     * 日志记录
     * @param logType 日志类型 Log.常量，0-登陆日志，1-操作日志，2-异常日志
     * @param ex 异常
     */
    public static void logging(HttpServletRequest request, Integer logType, Exception ex){
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
            }else {
                //错误日志和操作日志添加请求参数
                log.setRequestUrl(request.getRequestURI());
                log.setParams(getParams(request.getParameterMap()));
            }
            new SaveLogThread(log, ex).start();
        }
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread{

        private Log log;
        private Exception ex;

        public SaveLogThread(Log log, Exception ex){
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.ex = ex;
        }

        @Override
        public void run() {
            // 如果有异常，设置异常信息
            log.setExceptions(ExceptionUtils.getStackTraceAsString(ex));
            // 保存日志信息
            logMapper.insert(log);
        }
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
