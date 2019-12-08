package cn.gov.gsport.system.controller;

import cn.gov.gsport.core.base.BaseController;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.system.entity.Menu;
import cn.gov.gsport.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author shiva   2019/8/7 19:45
 */
@Controller
@RequestMapping("${adminPath}/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @ModelAttribute
    public Menu get(@RequestParam(required=false) Long id) {
        if (id != null){
//            return menuService.getById(id);
            return new Menu();
        }else{
            return new Menu();
        }
    }


    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "system/menuList";
    }


    @ResponseBody
    @RequestMapping(value = "findList")
    public Resp findList() {
        //获得全部菜单
        List<Menu> all = menuService.findAll();
        return Resp.success(null, all);
    }

    @RequestMapping(value = "delete")
    public String delete(Long id, RedirectAttributes model){
        menuService.deleteLogic(id);
        model.addFlashAttribute("resMsg", Resp.success("删除成功！",null));
        return "redirect:" + adminPath + "/menu";
    }


    /**
     * 编辑页面
     */
    @RequestMapping(value = "form")
    public String form(Menu menu, Model model){
        model.addAttribute("menu",menu);
        return "manage/sys/menuForm";
    }

    /**
     * 添加下级菜单控制器，需要整理返回office
     */
    @RequestMapping(value = "addChildMenu")
    public String addChildMenu(Menu menu, Model model){
        if (menu.getPid() != null){
//            menu.setParent(menuService.getById(menu.getPid()));
        }
        model.addAttribute("menu",menu);
        return "system/menuForm";
    }


    @RequestMapping(value = "save")
    public String save(Menu menu, RedirectAttributes model) {
        try {
            menuService.saveMenu(menu);
            model.addFlashAttribute("resMsg", Resp.success("保存成功！",null));
            return "redirect:" + adminPath + "/menu";
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO 异常界面，显示信息
        return "redirect:" + adminPath + "/menu";
    }


    /**
     * 弹出框树形部门菜单
     */
    @RequestMapping(value = "menuTree")
    public String menuTree(Model model){
        List<Menu> list = menuService.findAll();
        model.addAttribute("list",list);
        return "system/menuTree";
    }

}
