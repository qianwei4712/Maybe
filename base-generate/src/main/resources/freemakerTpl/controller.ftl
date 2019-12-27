package ${packageName}.${moduleName}.controller;

import cn.gov.gsport.core.base.BaseController;
import cn.gov.gsport.core.basic.Page;
import cn.gov.gsport.core.basic.Resp;
import ${packageName}.${moduleName}.entity.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;
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
 * ${functionName}控制器
 * @author ${classAuthor}  ${createTime}
 */
@Controller
@RequestMapping(value = "${r"${adminPath}"}/${urlPrefix}")
public class ${ClassName}Controller extends BaseController {

    @Autowired
    private ${ClassName}Service ${className}Service;

    @ModelAttribute
    public ${ClassName} get(@RequestParam(required=false) Long id) {
        if (id != null){
            return ${className}Service.getById(id);
        }else{
            return new ${ClassName}();
        }
    }

    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "modules/${viewPrefix}List";
    }

    @ResponseBody
    @RequestMapping(value = "findByPage")
    public Resp findByPage(HttpServletRequest request, HttpServletResponse response, ${ClassName} ${className}){
        try {
            Page<${ClassName}> page = ${className}Service.findByPage(request, response, ${className});
            return Resp.success(null, page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

    @RequestMapping(value = "form")
    public String form(${ClassName} ${className}, Model model){
        model.addAttribute("${className}",${className});
        return "modules/${viewPrefix}Form";
    }

    @RequestMapping(value = "save")
    public String save(${ClassName} ${className}, RedirectAttributes model) {
        try {
            ${className}Service.saveOrUpdate(${className});
            model.addFlashAttribute("resMsg", RESP_MSG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("resMsg", RESP_MSG_ERROR);
        }
        return "redirect:" + adminPath + "/${urlPrefix}";
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public Resp delete(Long id) {
        try {
            if (${className}Service.deleteLogic(id)){
                return Resp.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

}
