package cn.gov.gsport.core.config.templates;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 将thymeleaf模板中用到的全局变量返回
 * @author shiva   2019/12/6 19:33
 */
@Component
public class TemplateBaseProperties {

    @Resource
    private Environment env;

    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if(viewResolver != null) {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("adminPath", env.getProperty("adminPath"));
            vars.put("frontPath", env.getProperty("frontPath"));
            vars.put("productName", env.getProperty("productName"));
            viewResolver.setStaticVariables(vars);
        }
    }
}
