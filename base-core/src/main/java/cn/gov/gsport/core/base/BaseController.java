package cn.gov.gsport.core.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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


}
