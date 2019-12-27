package cn.gov.gsport.core.base;

import cn.gov.gsport.core.basic.Resp;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author shiva   2019/12/5 21:20
 */
public abstract class BaseController {

    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;

    /**
     * 前端基础路径
     */
    @Value("${frontPath}")
    protected String frontPath;

    /**
     * 操作成功，默认返回信息
     */
    protected static final Resp RESP_MSG_SUCCESS = Resp.success("操作成功！",null);

    /**
     * 操作失败，默认返回信息
     */
    protected static final Resp RESP_MSG_ERROR = Resp.error("操作失败，请联系运维人员！",null);

}
