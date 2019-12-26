package cn.gov.gsport.system.controller;

import cn.gov.gsport.core.base.BaseController;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.core.utils.SysUtils;
import cn.gov.gsport.system.entity.Office;
import cn.gov.gsport.system.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author shiva   2019/7/11 22:28
 */
@Controller
@RequestMapping("${adminPath}/office")
public class OfficeController extends BaseController {

    @Autowired
    private OfficeService officeService;

    @ModelAttribute
    public Office get(@RequestParam(required=false) Long id) {
        if (id != null){
            return officeService.getById(id);
        }else{
            return new Office();
        }
    }


    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "system/officeList";
    }

    @ResponseBody
    @RequestMapping(value = "findList")
    public Resp findList() {
        return Resp.success(null, SysUtils.getAllOfficeList());
    }


    @RequestMapping(value = "delete", name = "删除部门")
    public String delete(Long id, RedirectAttributes model){
        //顶级部门不允许删除
        if (id==null || id == 1){
            model.addFlashAttribute("resMsg", Resp.error("顶级部门不允许删除！",null));
            return "redirect:" + adminPath + "/office";
        }
        officeService.deleteLogic(id);
        model.addFlashAttribute("resMsg", Resp.success("删除成功！",null));
        return "redirect:" + adminPath + "/office";
    }


    /**
     * 编辑页面
     */
    @RequestMapping(value = "form")
    public String form(Office office, Model model){
        model.addAttribute("office",office);
        return "system/officeForm";
    }

    /**
     * 添加下级菜单控制器，需要整理返回office
     */
    @RequestMapping(value = "addChildOffice")
    public String addChildOffice(Office office, Model model){
        if (office.getPid() != null){
            office.setParent(officeService.getById(office.getPid()));
        }
        model.addAttribute("office",office);
        return "system/officeForm";
    }


    @RequestMapping(value = "save", name = "编辑部门")
    public String save(Office office, RedirectAttributes model) {
        try {
            officeService.saveOffice(office);
            model.addFlashAttribute("resMsg", Resp.success("保存成功！",null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + adminPath + "/office";
    }

    /**
     * 弹出框树形部门菜单
     */
    @RequestMapping(value = "officeTree")
    public String officeTree(Model model){
        model.addAttribute("list",SysUtils.getAllOfficeList());
        return "system/officeTree";
    }

}
