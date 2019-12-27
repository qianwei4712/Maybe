package cn.gov.gsport.system.entity;

import cn.gov.gsport.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
* 系统日志实体类
* @author shiva  2019-12-23
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class Log extends BaseEntity<Log> {

    /**
     *日志标题
     */
    private String title;

    /**
     *当前用户id
     */
    private Long userid;

    /**
     *当前用户名
     */
    private String username;

    /**
     *日志类型，0-登陆日志，1-操作日志，2-异常日志
     */
    private Integer logType;

    /**
     *登陆ip
     */
    private String remoteAddr;

    /**
     *用户代理
     */
    private String userAgent;

    /**
     *请求链接
     */
    private String requestUrl;

    /**
     *参数
     */
    private String params;

    /**
     *异常信息
     */
    private String exceptions;


    private Date createDateStart;
    private Date createDateEnd;



    /**
     * 登陆日志类型
     */
    public static final Integer LOGIN_LOG = 0;
    /**
     * 操作日志类型
     */
    public static final Integer OPERATE_LOG = 1;
    /**
     * 异常日志类型
     */
    public static final Integer ERROR_LOG = 2;

}
