package cn.shiva.modules.test.controller;

import cn.shiva.core.base.BaseController;
import cn.shiva.core.basic.Page;
import cn.shiva.core.basic.Resp;
import cn.shiva.core.utils.LogUtils;
import cn.shiva.modules.test.entity.Demo;
import cn.shiva.modules.test.service.DemoService;
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
            LogUtils.exceptionCatch(request, e);
        }
        return Resp.error();
    }

    @RequestMapping(value = "form")
    public String form(Demo demo, Model model){
        model.addAttribute("demo",demo);
        return "modules/test/demoForm";
    }

    @RequestMapping(value = "save")
    public String save(Demo demo, RedirectAttributes model, HttpServletRequest request) {
        try {
            demoService.saveOrUpdate(demo);
            return "redirect:" + adminPath + "/demo";
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.exceptionCatch(request, e);
        }
        return "redirect:" + adminPath + "/demo";
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public Resp delete(Long id, HttpServletRequest request) {
        try {
            if (demoService.deleteLogic(id)){
                return Resp.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.exceptionCatch(request, e);
        }
        return Resp.error();
    }

}
