package cn.gov.gsport.system.mapper;

import cn.gov.gsport.core.base.BaseMapper;
import cn.gov.gsport.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author shiva   2019/7/6 17:01
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名username选择
     * @param username 用户名
     * @return 返回用户对象
     */
    User findByUsername(String username);

    /**
     * 根据用户主键ID删除中间表用户-角色所有信息
     * @param id 主键ID
     */
    void clearUserRole(Long id);

    /**
     * 批量插入中间表用户-角色
     * @param id 用户主键ID
     * @param roleIds 角色ID数组
     */
    void insertUserRole(@Param("id") Long id, @Param("roleIds") String[] roleIds);
}
