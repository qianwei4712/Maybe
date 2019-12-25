package cn.gov.gsport.system.controller;

import cn.gov.gsport.core.base.BaseController;
import cn.gov.gsport.core.basic.Page;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.system.entity.Log;
import cn.gov.gsport.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志控制器
 * @author shiva  2019-12-23
 */
@Controller
@RequestMapping(value = "${adminPath}/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @ModelAttribute
    public Log get(@RequestParam(required=false) Long id) {
        if (id != null){
            return logService.getById(id);
        }else{
            return new Log();
        }
    }

    @RequestMapping(value = {"login", "operate", "error"})
    public String list(HttpServletRequest request, Model model) {
        StringBuffer url = request.getRequestURL();
        String pageName = url.substring(url.indexOf("/log/") + 5);
        switch (pageName){
            case "operate": return "system/log" + "Operate";
            case "error": return "system/log" + "Error";
            default: return "system/log" + "Login";
        }
    }

    @ResponseBody
    @RequestMapping(value = "findByPage")
    public Resp findByPage(HttpServletRequest request, HttpServletResponse response, Log log){
        try {
            Page<Log> page = logService.findByPage(request, response, log);
            return Resp.success(null, page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

}
