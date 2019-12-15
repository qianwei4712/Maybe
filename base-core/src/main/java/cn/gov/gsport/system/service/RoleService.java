package cn.gov.gsport.system.service;

import cn.gov.gsport.common.others.CommonUtils;
import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.core.constant.BaseConstant;
import cn.gov.gsport.core.utils.SysUtils;
import cn.gov.gsport.system.entity.Role;
import cn.gov.gsport.system.mapper.MenuMapper;
import cn.gov.gsport.system.mapper.RoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shiva   2019/8/11 22:30
 */
@Service
public class RoleService extends BaseService<Role, RoleMapper> {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;

    public Role getById(Long id){
        Role role = roleMapper.selectById(id);
        if (role == null){
            return role;
        }
        role.setMenuList(SysUtils.getMenuListByRoleId(id));
        role.menuIdsInit();
        return role;
    }


    /**
     * 保存角色，清空原有角色菜单对应中间表，重新添加
     */
    @Transactional(readOnly = false)
    public void saveRole(Role role) {
        //保存role
        saveOrUpdate(role);
        //先清空
        roleMapper.clearRoleMenu(role.getId());
        //保存中间表
        String menuIds = role.getMenuIds();
        if (!StringUtils.isBlank(menuIds)){
            menuIds = CommonUtils.subEndComma(menuIds);
            String[] split = menuIds.split(BaseConstant.SPLIT_SYMBOL);
            roleMapper.insertRoleMenu(role.getId(), split);
        }

        SysUtils.clearMenuCache();
    }


    /**
     * 查询所有启用状态的角色
     */
    public List<Role> getEnableRoles(){
        return roleMapper.getEnableRoles();
    }

}
