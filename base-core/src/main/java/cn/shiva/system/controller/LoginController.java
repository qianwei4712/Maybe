package cn.shiva.system.controller;

import cn.shiva.common.others.CommonUtils;
import cn.shiva.common.web.Servelets;
import cn.shiva.core.base.BaseController;
import cn.shiva.core.utils.LogUtils;
import cn.shiva.core.utils.SysUtils;
import cn.shiva.system.entity.Log;
import cn.shiva.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author shiva   2019/7/7 0:09
 */
@Controller
public class LoginController extends BaseController{

    /**
     * 登陆
     */
    @RequestMapping(value= {"/login"})
    public String login(HttpServletRequest request, Map<String, Object> map) {
        User user = SysUtils.getUser();
        // 如果已经登录，则跳转到管理首页
        if(user.getId() != null){
            return "redirect:" + adminPath + "/index";
        }
        try {
            //获取登录名
            String username = request.getParameter("username");
            if(StringUtils.isBlank(username)){
                //若登录名为空，则返回登陆界面
                return "system/sysLogin";
            }
            //获取密码
            String password = request.getParameter("password");
            //加盐。加密，反回32位密码
            String md5Pwd = CommonUtils.md5HashWithusername(username, password);
            //传递token给shiro的realm
            UsernamePasswordToken token = new UsernamePasswordToken(username, md5Pwd);

            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            //登录日志记录
            LogUtils.logging(Servelets.getRequest(), Log.LOGIN_LOG);
            return "redirect:" + adminPath + "/index";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "system/sysLogin";
    }

    /**
     * 登出
     */
    @RequestMapping(value= {"/logout"})
    public String logout(HttpServletRequest request, Map<String, Object> map) {
        SecurityUtils.getSubject().logout();
        return "system/sysLogin";
    }

}
