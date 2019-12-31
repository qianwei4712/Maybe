package cn.shiva.core.config;

import cn.shiva.common.lang.StringUtils;
import cn.shiva.core.constant.BaseConstant;
import cn.shiva.core.utils.CacheUtils;
import cn.shiva.core.utils.SysUtils;
import cn.shiva.system.entity.Menu;
import cn.shiva.system.entity.Role;
import cn.shiva.system.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * @author shiva   2019/7/6 23:04
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        //通过工具类，先查缓存，再差数据库，获得用户
        User user = SysUtils.get(username);
        if(user == null){
            return null;
        }
        //直接将用户作为缓存，密码、realm name
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                user.getName()
        );
    }

    /**
     * 获取权限授权信息，如果缓存中存在，则直接从缓存中获取，否则就重新获取， 登录成功后调用
     */
    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }
        AuthorizationInfo info = null;
        info = (AuthorizationInfo) CacheUtils.get(SysUtils.AUTH_INFO_KEY);
        if (info == null) {
            info = doGetAuthorizationInfo(principals);
            if (info != null) {
                CacheUtils.put(SysUtils.AUTH_INFO_KEY, info);
            }
        }
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) getAvailablePrincipal(principals);
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Menu> list = SysUtils.getAllMenuList();
            for (Menu menu : list){
                if (StringUtils.isNotBlank(menu.getPermission())){
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(), BaseConstant.SPLIT_SYMBOL)){
                        info.addStringPermission(permission);
                    }
                }
            }
            // 添加用户权限
            info.addStringPermission("user");
            // 添加用户角色信息
            for (Role role : user.getRoleList()){
                info.addRole(role.getEnname());
            }
            return info;
        } else {
            return null;
        }
    }

}
