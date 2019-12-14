package cn.gov.gsport.core.utils;

import cn.gov.gsport.system.entity.Dict;
import cn.gov.gsport.system.entity.User;
import cn.gov.gsport.system.mapper.DictMapper;
import cn.gov.gsport.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shiva   2019/11/29 22:36
 */
public class SysUtils {

    private static UserService userService = SpringContextHolder.getBean(UserService.class);;
    private static DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);;

    /**
     * 缓存中，key的前缀，区分不同缓存
     */
    private static final String PRE_USERNAME = "username:";
    private static final String PRE_ID = "id:";




    /*
     ******************************************************************************************************************
     *  用户相关方法
     ******************************************************************************************************************
     */


    /**
     * 根据username获取用户
     * @param username 用户名
     * @return 取不到返回null
     */
    public static User get(String username){
        User user = (User)CacheUtils.get(CacheUtils.USER_CACHE, PRE_USERNAME + username);
        if (user ==  null){
            user = userService.findByUsername(username);
            if (user == null){
                return null;
            }
            user = userService.getById(user.getId());
            //根据username获得用户
            CacheUtils.put(CacheUtils.USER_CACHE, PRE_USERNAME + username, user);
            //根据id获得用户
            CacheUtils.put(CacheUtils.USER_CACHE, PRE_ID + user.getId(), user);
        }
        return user;
    }

    /**
     * 根据id获取用户
     * @param id 用户主键ID
     * @return 取不到返回null
     */
    public static User get(Long id){
        User user = (User)CacheUtils.get(CacheUtils.USER_CACHE, PRE_ID + id);
        if (user ==  null){
            user = userService.getById(id);
            if (user == null){
                return null;
            }
            //根据username获得用户
            CacheUtils.put(CacheUtils.USER_CACHE, PRE_USERNAME + user.getUsername(), user);
            //根据id获得用户
            CacheUtils.put(CacheUtils.USER_CACHE, PRE_ID + id, user);
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

    /**
     * 根据用户id, 清理用户缓存
     */
    public static void clearUserCache(Long id) {
       CacheUtils.remove(CacheUtils.USER_CACHE, PRE_ID + id);
    }

    /*
     ******************************************************************************************************************
     *  字典相关方法
     ******************************************************************************************************************
     */

    /**
     * 根据类型获取相关字典列表
     * @param type 字典类型
     * @return 指定类型字典列表
     */
    public static List<Dict> getDictList(String type){
        @SuppressWarnings("unchecked")
        Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CacheUtils.DICT_MAP_CACHE);
        if (dictMap==null){
            dictMap = new HashMap<String, List<Dict>>();
            for (Dict dict : dictMapper.findAll()){
                List<Dict> dictList = dictMap.get(dict.getType());
                if (dictList != null){
                    dictList.add(dict);
                }else{
                    List<Dict> list = new ArrayList<>();
                    list.add(dict);
                    dictMap.put(dict.getType(), list);
                }
            }
            CacheUtils.put(CacheUtils.DICT_MAP_CACHE, dictMap);
        }
        List<Dict> dictList = dictMap.get(type);
        if (dictList == null){
            dictList =new ArrayList<>();
        }
        return dictList;
    }

}
