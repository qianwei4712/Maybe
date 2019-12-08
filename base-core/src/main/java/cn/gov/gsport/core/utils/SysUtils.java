package cn.gov.gsport.core.utils;

import cn.gov.gsport.system.entity.User;
import cn.gov.gsport.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author shiva   2019/11/29 22:36
 */
public class SysUtils {

    private static UserService userService = SpringContextHolder.getBean(UserService.class);;

    /**
     * 缓存中，key的前缀，区分不同缓存
     */
    private static final String PRE_USERNAME = "username:";
    private static final String PRE_ID = "id:";


    /**
     * 根据username获取用户
     * @param username 用户名
     * @return 取不到返回null
     */
    public static User get(String username){
        User user = (User)CacheUtils.get(CacheUtils.SYS_CACHE, PRE_USERNAME + username);
        if (user ==  null){
            user = userService.findByUsername(username);
            if (user == null){
                return null;
            }
            user = userService.getById(user.getId());
            //根据username获得用户
            CacheUtils.put(CacheUtils.SYS_CACHE, PRE_USERNAME + username, user);
            //根据id获得用户
            CacheUtils.put(CacheUtils.SYS_CACHE, PRE_ID + user.getId(), user);
        }
        return user;
    }

    /**
     * 根据id获取用户
     * @param id 用户主键ID
     * @return 取不到返回null
     */
    public static User get(Long id){
        User user = (User)CacheUtils.get(CacheUtils.SYS_CACHE, PRE_ID + id);
        if (user ==  null){
            user = userService.getById(id);
            if (user == null){
                return null;
            }
            //根据username获得用户
            CacheUtils.put(CacheUtils.SYS_CACHE, PRE_USERNAME + user.getUsername(), user);
            //根据id获得用户
            CacheUtils.put(CacheUtils.SYS_CACHE, PRE_ID + id, user);
        }
        return user;
    }


    /**
     * 获取当前登录者对象
     */
    public static User getUser(){
        try{
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getPrincipal();
            if (user != null){
                return user;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }
}
