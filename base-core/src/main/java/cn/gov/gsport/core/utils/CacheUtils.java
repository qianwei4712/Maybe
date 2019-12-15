package cn.gov.gsport.core.utils;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.Iterator;
import java.util.Set;

/**
 * @author shiva   2019/7/29 20:53
 */
public class CacheUtils {

    private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);;

    /**
     * 系统缓存：部门、字典、地区信息
     */
    public static final String SYS_CACHE = "sysCache";
    /**
     * 系统缓存：用户
     */
    public static final String USER_CACHE = "userCache";
    /**
     * 系统缓存：菜单
     */
    public static final String MENU_CACHE = "menuCache";

    /**
     * 获取SYS_CACHE缓存
     * @param key
     */
    public static Object get(String key) {
        return get(SYS_CACHE, key);
    }

    /**
     * 获取SYS_CACHE缓存
     * @param key
     * @param defaultValue
     */
    public static Object get(String key, Object defaultValue) {
        Object value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 写入SYS_CACHE缓存
     * @param key
     * @return
     */
    public static void put(String key, Object value) {
        put(SYS_CACHE, key, value);
    }

    /**
     * 从SYS_CACHE缓存中移除
     * @param key
     * @return
     */
    public static void remove(String key) {
        remove(SYS_CACHE, key);
    }

    /**
     * 获取缓存
     * @param cacheName
     * @param key
     * @return
     */
    public static Object get(String cacheName, String key) {
        return getCache(cacheName).get(getKey(key));
    }

    /**
     * 获取缓存
     * @param cacheName
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String cacheName, String key, Object defaultValue) {
        Object value = get(cacheName, getKey(key));
        return value != null ? value : defaultValue;
    }

    /**
     * 写入缓存
     * @param cacheName
     * @param key
     * @param value
     */
    public static void put(String cacheName, String key, Object value) {
        getCache(cacheName).put(getKey(key), value);
    }

    /**
     * 从缓存中移除
     * @param cacheName
     * @param key
     */
    public static void remove(String cacheName, String key) {
        getCache(cacheName).remove(getKey(key));
    }

    /**
     * 从缓存中移除所有
     * @param cacheName
     */
    public static void removeAll(String cacheName) {
        Cache<String, Object> cache = getCache(cacheName);
        Set<String> keys = cache.keys();
        for (Iterator<String> it = keys.iterator(); it.hasNext();){
            cache.remove(it.next());
        }
    }

    /**
     * 获取缓存键名，多数据源下增加数据源名称前缀
     * @param key
     * @return
     */
    private static String getKey(String key){
        return key;
    }

    /**
     * 获得一个Cache，没有则显示日志。
     * @param cacheName
     * @return
     */
    private static Cache<String, Object> getCache(String cacheName){
        Cache<String, Object> cache = cacheManager.getCache(cacheName);
        if (cache == null){
            throw new RuntimeException("当前系统中没有定义“"+cacheName+"”这个缓存。");
        }
        return cache;
    }






}
