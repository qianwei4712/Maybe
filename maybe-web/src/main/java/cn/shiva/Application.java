package cn.shiva;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Application
 * @author shiva
 * @version 2019-12-03
 */
@MapperScan({"cn.shiva.system.mapper","cn.shiva.modules.*.mapper"})
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 错误页面有容器来处理，而不是SpringBoot
        this.setRegisterErrorPageFilter(false);
        return builder.sources(Application.class);
    }
	
}