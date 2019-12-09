package cn.gov.gsport.system.controller;

import cn.gov.gsport.common.others.CommonUtils;
import cn.gov.gsport.core.base.BaseController;
import cn.gov.gsport.core.basic.Page;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.core.utils.SysUtils;
import cn.gov.gsport.system.entity.Office;
import cn.gov.gsport.system.entity.User;
import cn.gov.gsport.system.service.OfficeService;
import cn.gov.gsport.system.service.RoleService;
import cn.gov.gsport.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author shiva   2019/7/9 23:11
 */
@Controller
@RequestMapping("${adminPath}/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private RoleService roleService;

    @ModelAttribute
    public User get(@RequestParam(required=false) Long id) {
        if (id != null){
            return userService.getById(id);
        }else{
            return new User();
        }
    }

    /**
     * 打开列表界面
     */
    @RequestMapping(value = {"list",""})
    public String list(Model model) {
        List<Office> list = officeService.findAll();
        model.addAttribute("list",list);
        return "system/userList";
    }

    /**
     * 分页请求列表界面数据
     */
    @ResponseBody
    @RequestMapping(value = "findByPage")
    public Resp findByPage(HttpServletRequest request, HttpServletResponse response, User user){
        try {
            Page<User> page = userService.findByPage(request, response, user);
            return Resp.success(null, page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }


    /**
     * 表单编辑界面
     */
    @RequestMapping(value = "form")
    public String form(User user, Model model){
        user.roleIdsInit();
        model.addAttribute("roleList",roleService.getEnableRoles());
        model.addAttribute("user",user);
        return "system/userForm";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "save")
    public String save(User user, RedirectAttributes model) {
        try {
            userService.saveUser(user);
            return "redirect:" + adminPath + "/user";
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO 异常界面，显示信息
        return "redirect:" + adminPath + "/timeline";
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public Resp delete(Long id) {
        try {
            if (userService.deleteLogic(id)){
                return Resp.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }


    /**
     * 修改密码界面
     */
    @RequestMapping("/modifyPwdPage")
    public String modifyPwdPage(Model model) {
        return "system/modifyPwdPage";
    }

    /**
     * 保存密码修改
     */
    @RequestMapping("/modifyPwd")
    public String modifyPwd(String oldPassword, String newPassword, RedirectAttributes model) {
        User user = SysUtils.getUser();
        if(user != null && user.getId() != null){
            return "redirect:/logout";
        }
        String md5Pwd = CommonUtils.md5HashWithusername(user.getUsername(), oldPassword);
        if (!user.getPassword().equals(md5Pwd)){
            //原密码不正确
            model.addFlashAttribute("resMsg", Resp.error("原密码错误！",null));
            return "redirect:" + adminPath + "/user/modifyPwdPage";
        }else {
            //原密码正确
            String newMd5Pwd =  CommonUtils.md5HashWithusername(user.getUsername(), newPassword);
            user.setPassword(newMd5Pwd);
            userService.saveOrUpdate(user);
            SysUtils.clearUserCache(user.getId());
            return "redirect:/logout";
        }
    }

    /**
     * ajax切换用户登陆状态
     */
    @ResponseBody
    @RequestMapping("/changeStatus")
    public Resp changeStatus(Long id, String status) {
        if (userService.changeStatus(id, status)){
            return Resp.success();
        }
        return Resp.error();
    }

    /**
     * 重置密码为123456
     */
    @ResponseBody
    @RequestMapping("/resetPwd")
    public Resp resetPwd(Long id) {
        try {
            return userService.resetPwd(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Resp.error("重置密码出错",null);
        }
    }

}
