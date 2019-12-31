package cn.shiva.core.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author shiva   2019/8/14 23:36
 */
public interface BaseMapper<T>{

    /**
     * 根据主键ID获取对象
     * @param id 主键id
     * @return 返回对象
     */
    T selectById(Serializable id);

    /**
     * 根据主键ID更改status字段状态
     * @param id 主键id
     * @param status 状态值
     * @return 0表示失败，1表示成功
     */
    Integer changeStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 查询全部
     * @return 返回对象列表
     */
    List<T> findAll();

    /**
     * 查询列表，专用于分页查询
     * @param t 查询条件
     * @return 返回对象列表
     */
    List<T> findList(T t);

    /**
     * 根据主键ID逻辑删除
     * @param id 主键ID
     * @return 影响行数
     */
    Integer deleteLogic(Serializable id);

    /**
     * 插入对象
     * @param t 对象
     * @return 影响行数
     */
    Integer insert(T t);

    /**
     * 根据主键ID更新对象
     * @param t 对象
     * @return 影响行数
     */
    Integer updateById(T t);
}
