package cn.gov.gsport.modules.test.controller;

import cn.gov.gsport.core.base.BaseController;
import cn.gov.gsport.core.basic.Page;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.modules.test.entity.Demo;
import cn.gov.gsport.modules.test.service.DemoService;
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
 * 演示模块
 * @author shiva   2019/12/15 22:20
 */
@Controller
@RequestMapping(value = "${adminPath}/test/demo")
public class DemoController extends BaseController {

    @Autowired
    private DemoService demoService;

    @ModelAttribute
    public Demo get(@RequestParam(required=false) Long id) {
        if (id != null){
            return demoService.getById(id);
        }else{
            return new Demo();
        }
    }

    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "modules/test/demoList";
    }

    @ResponseBody
    @RequestMapping(value = "findByPage")
    public Resp findByPage(HttpServletRequest request, HttpServletResponse response, Demo demo){
        try {
            Page<Demo> page = demoService.findByPage(request, response, demo);
            return Resp.success(null, page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

    @RequestMapping(value = "form")
    public String form(Demo demo, Model model){
        model.addAttribute("demo",demo);
        return "modules/test/demoForm";
    }

    @RequestMapping(value = "save")
    public String save(Demo demo, RedirectAttributes model) {
        try {
            demoService.saveOrUpdate(demo);
            return "redirect:" + adminPath + "/demo";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + adminPath + "/demo";
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public Resp delete(Long id) {
        try {
            if (demoService.deleteLogic(id)){
                return Resp.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

}
