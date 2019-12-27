package cn.gov.gsport.system.controller;

import cn.gov.gsport.core.base.BaseController;
import cn.gov.gsport.core.basic.Page;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.core.utils.SysUtils;
import cn.gov.gsport.system.entity.Role;
import cn.gov.gsport.system.service.RoleService;
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

/**
 * @author shiva   2019/8/11 22:28
 */
@Controller
@RequestMapping("${adminPath}/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ModelAttribute
    public Role get(@RequestParam(required=false) Long id) {
        if (id != null){
            return roleService.getById(id);
        }else{
            return new Role();
        }
    }


    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "system/roleList";
    }


    /**
     * 分页请求列表界面数据
     */
    @ResponseBody
    @RequestMapping(value = "findByPage")
    public Resp findByPage(HttpServletRequest request, HttpServletResponse response, Role role){
        try {
            Page<Role> page = roleService.findByPage(request, response, role);
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
    public String form(Role role, Model model){
        role.menuIdsInit();
        model.addAttribute("list", SysUtils.getAllMenuList());
        model.addAttribute("role",role);
        return "system/roleForm";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "save", name = "编辑角色")
    public String save(Role role, RedirectAttributes model) {
        try {
            roleService.saveRole(role);
            model.addFlashAttribute("resMsg", RESP_MSG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("resMsg", RESP_MSG_ERROR);
        }
        return "redirect:" + adminPath + "/role";
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete", name = "删除角色")
    public Resp delete(Long id) {
        try {
            if (roleService.deleteLogic(id)){
                return Resp.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

    /**
     * ajax切换角色登陆状态
     */
    @ResponseBody
    @RequestMapping(value = "/changeStatus", name = "切换角色状态")
    public Resp changeStatus(Long id, String status) {
        if (roleService.changeStatus(id, status)){
            return Resp.success();
        }
        return Resp.error();
    }

}
