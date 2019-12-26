package cn.gov.gsport.system.service;

import cn.gov.gsport.common.lang.StringUtils;
import cn.gov.gsport.common.others.CommonUtils;
import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.core.constant.BaseConstant;
import cn.gov.gsport.core.utils.SysUtils;
import cn.gov.gsport.system.entity.Role;
import cn.gov.gsport.system.entity.User;
import cn.gov.gsport.system.mapper.RoleMapper;
import cn.gov.gsport.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author shiva   2019/7/6 17:03
 */
@Service
public class UserService extends BaseService<User, UserMapper> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 根据用户ID获取对象
     */
    @Override
    public User getById(Serializable id){
        User user = userMapper.selectById(id);
        if (user == null){
            return null;
        }
        List<Role> roles = roleMapper.selectRoleListByUserId(user.getId());
        user.setRoleList(roles);
        user.roleIdsInit();
        return user;
    }


    /**
     * 更新,重新设置密码
     */
    @Override
    public boolean saveOrUpdate(User user) {
        if (user !=null && StringUtils.isBlank(user.getPassword())){
            //若无密码，则新增密码
            String md5pwd = CommonUtils.md5HashWithusername(user.getUsername(), BaseConstant.DEFAULT_PASSWORD);
            user.setPassword(md5pwd);
        }
        return super.saveOrUpdate(user);
    }

    /**
     * 保存用户，需重新保存用户角色对应信息
     */
    @Transactional(readOnly = false)
    public void saveUser(User user) {
        //保存role
        saveOrUpdate(user);
        //先清空
        userMapper.clearUserRole(user.getId());
        //保存中间表
        String roleIds = user.getRoleIds();
        if (!StringUtils.isBlank(roleIds)){
            roleIds = CommonUtils.subEndComma(roleIds);
            String[] split = roleIds.split(BaseConstant.SPLIT_SYMBOL);
            userMapper.insertUserRole(user.getId(), split);
        }
        SysUtils.clearUserCache(user.getId());
    }

    /**
     * 重置密码为123456
     */
    public Resp resetPwd(Long id) {
        User user = SysUtils.get(id);
        if (user == null){
            return Resp.error("用户不存在",null);
        }
        user.setPassword(CommonUtils.md5HashWithusername(user.getUsername(), BaseConstant.DEFAULT_PASSWORD));
        return Resp.success();
    }

}
