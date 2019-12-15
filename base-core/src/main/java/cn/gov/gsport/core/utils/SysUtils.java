package cn.gov.gsport.core.utils;

import cn.gov.gsport.system.entity.*;
import cn.gov.gsport.system.mapper.DictMapper;
import cn.gov.gsport.system.mapper.MenuMapper;
import cn.gov.gsport.system.mapper.OfficeMapper;
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
    private static MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);;
    private static OfficeMapper officeMapper = SpringContextHolder.getBean(OfficeMapper.class);;

    /**
     * 缓存中，key的前缀，区分不同缓存
     */
    private static final String PRE_USERNAME = "username:";
    private static final String PRE_ID = "id:";

    /**
     * 字典缓存中的key名
     */
    private static final String DICT_CACHE_KEY = "dictCacheKey";
    /**
     * 角色菜单缓存中的key名，全部菜单
     */
    private static final String MENU_ALL_KEY = "menuAllKey";
    /**
     * 部门缓存中的key名，全部部门
     */
    private static final String OFFICE_ALL_KEY = "officeAllKey";
    /**
     * 地区信息缓存中的key名，全部地区信息
     */
    private static final String AREA_ALL_KEY = "areaAllKey";





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
        Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CacheUtils.SYS_CACHE, DICT_CACHE_KEY);
        if (dictMap==null){
            dictMap = new HashMap<>();
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
            CacheUtils.put(CacheUtils.SYS_CACHE, DICT_CACHE_KEY,  dictMap);
        }
        List<Dict> dictList = dictMap.get(type);
        if (dictList == null){
            dictList =new ArrayList<>();
        }
        return dictList;
    }

    /**
     * 更改字典时调用，清除字典缓存
     */
    public static void clearDictCache(){
        CacheUtils.remove(CacheUtils.SYS_CACHE, DICT_CACHE_KEY);
    }



    /*
     ******************************************************************************************************************
     *  角色菜单相关方法
     ******************************************************************************************************************
     */

    /**
     * 根据角色ID获取菜单权限列表
     * @param roleId 角色ID
     * @return 该角色拥有的菜单权限列表
     */
    public static List<Menu> getMenuListByRoleId(Long roleId){
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>)CacheUtils.get(CacheUtils.MENU_CACHE, PRE_ID + roleId);
        if (menuList == null){
            menuList = menuMapper.selectMenuListByRoleId(roleId);
            CacheUtils.put(CacheUtils.MENU_CACHE, PRE_ID + roleId, menuList);
        }
        return menuList;
    }

    /**
     * @return 获得全部菜单
     */
    public static List<Menu> getAllMenuList(){
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>)CacheUtils.get(CacheUtils.MENU_CACHE, MENU_ALL_KEY);
        if (menuList == null){
            menuList = menuMapper.findAll();
            CacheUtils.put(CacheUtils.MENU_CACHE, MENU_ALL_KEY, menuList);
        }
        return menuList;
    }

    /**
     * 更改角色和菜单时调用，清除菜单缓存
     */
    public static void clearMenuCache(){
        CacheUtils.removeAll(CacheUtils.MENU_CACHE);
    }

    /*
     ******************************************************************************************************************
     *  机构部门相关方法
     ******************************************************************************************************************
     */

    /**
     * @return 获取当前用户有权限访问的部门
     */
    public static List<Office> getAllOfficeList(){
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>)CacheUtils.get(CacheUtils.SYS_CACHE, OFFICE_ALL_KEY);
        if (officeList == null){
            officeList = officeMapper.findAll();
            CacheUtils.put(CacheUtils.SYS_CACHE, OFFICE_ALL_KEY, officeList);
        }
        return officeList;
    }

    /**
     * 更改部门时调用，清除部门缓存
     */
    public static void clearOfficeCache(){
        CacheUtils.remove(CacheUtils.SYS_CACHE, OFFICE_ALL_KEY);
    }


    /*
     ******************************************************************************************************************
     *  地区相关方法
     ******************************************************************************************************************
     */


}
