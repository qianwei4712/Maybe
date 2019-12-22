package cn.gov.gsport.core.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.gov.gsport.core.utils.CacheUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 * @author shiva   2019/7/6 21:42
 */
@Configuration
public class ShiroConfig {

    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    private String adminPath;

    /**
     * 前端基础路径
     */
    @Value("${frontPath}")
    private String frontPath;

    /**
     * 加入thymeleaf页面权限配置
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 配置shiro过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //定义shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //LinkedHashMap是有序的，进行顺序拦截器配置
        Map<String,String> filterChainMap = new LinkedHashMap<String,String>();

        //配置和静态资源不会被拦截的链接 顺序判断
        filterChainMap.put(frontPath + "/**", "anon");
        filterChainMap.put("/static/**", "anon");

        //所有url必须通过认证才可以访问
        filterChainMap.put(adminPath + "/**", "authc");
        //设置默认登录的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置成功之后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //设置未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //设置shiroFilterFactoryBean的FilterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 配置安全管理器
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(systemAuthorizingRealm());

        //配置 ehcache缓存管理器 参考博客：
        securityManager.setCacheManager(ehCacheManager());

        return securityManager;
    }

    /**
     * 缓存管理器
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache.xml");
        return cacheManager;
    }


    /**
     * 自定义realm
     */
    @Bean
    public SystemAuthorizingRealm systemAuthorizingRealm() {
        SystemAuthorizingRealm systemAuthorizingRealm = new SystemAuthorizingRealm();
        //启用缓存
        systemAuthorizingRealm.setCachingEnabled(true);
        //设置缓存管理器
        systemAuthorizingRealm.setCacheManager(ehCacheManager());
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        systemAuthorizingRealm.setAuthenticationCacheName(CacheUtils.SYS_CACHE);
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        systemAuthorizingRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        systemAuthorizingRealm.setAuthorizationCacheName(CacheUtils.SYS_CACHE);

        return systemAuthorizingRealm;
    }


}
