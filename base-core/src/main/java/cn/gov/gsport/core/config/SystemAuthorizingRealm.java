package cn.gov.gsport.core.config;

import cn.gov.gsport.core.utils.SysUtils;
import cn.gov.gsport.system.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author shiva   2019/7/6 23:04
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        return authorizationInfo;
    }

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
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                user.getName()
        );
        return authenticationInfo;
    }





}
