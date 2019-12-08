package cn.gov.gsport.system.mapper;

import cn.gov.gsport.core.base.BaseMapper;
import cn.gov.gsport.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shiva   2019/7/6 21:14
 */
@Mapper
@Component
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 根据角色主键ID删除中间表角色-菜单所有信息-
     * @param id 角色主键ID
     */
    void clearRoleMenu(Long id);

    /**
     * 批量插入中间表角色-菜单
     * @param id 角色主键ID
     * @param menuIds 菜单ID数组
     */
    void insertRoleMenu(@Param("id") Long id, @Param("menuIds") String[] menuIds);

    /**
     * 获得全部启用的角色
     * @return 角色列表
     */
    List<Role> getEnableRoles();

    /**
     * 根据用户id查询角色列表，角色状态为正常
     * @param id 用户主键ID
     * @return 角色列表
     */
    List<Role> selectRoleListByUserId(@Param("id") Long id);
}
