package cn.shiva.core.utils.properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @author shiva   2020/1/2 19:19
 */
@Configuration
class BeanConfiguration {
    @Bean
    public PropUtil propertiesUtil() {
        //1:加载配置文件
        Resource app = new ClassPathResource("config/application.yml");
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        // 2:将加载的配置文件交给 YamlPropertiesFactoryBean
        yamlPropertiesFactoryBean.setResources(app);
        // 3：将yml转换成 key：val
        Properties properties = yamlPropertiesFactoryBean.getObject();
        // 4: 将Properties 通过构造方法交给我们写的工具类
        return new PropUtil(properties);
    }
}
