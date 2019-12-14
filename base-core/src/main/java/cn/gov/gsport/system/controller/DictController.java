package cn.gov.gsport.system.controller;

import cn.gov.gsport.core.base.BaseController;
import cn.gov.gsport.core.basic.Page;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.system.entity.Dict;
import cn.gov.gsport.system.service.DictService;
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
 * @author shiva   2019/12/14 18:44
 */
@Controller
@RequestMapping(value = "${adminPath}/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    @ModelAttribute
    public Dict get(@RequestParam(required=false) Long id) {
        if (id != null){
            return dictService.getById(id);
        }else{
            return new Dict();
        }
    }


    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "system/dictList";
    }


    /**
     * 分页请求列表界面数据
     */
    @ResponseBody
    @RequestMapping(value = "findByPage")
    public Resp findByPage(HttpServletRequest request, HttpServletResponse response, Dict dict){
        try {
            Page<Dict> page = dictService.findByPage(request, response, dict);
            return Resp.success(null, page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

    /**
     * 编辑界面
     */
    @RequestMapping(value = "form")
    public String form(Dict dict, Model model){
        model.addAttribute("dict",dict);
        return "system/dictForm";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "save")
    public String save(Dict dict, RedirectAttributes model) {
        try {
            dictService.saveOrUpdate(dict);
            return "redirect:" + adminPath + "/dict";
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO 异常界面，显示信息
        return "redirect:" + adminPath + "/dict";
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public Resp delete(Long id) {
        try {
            if (dictService.deleteLogic(id)){
                return Resp.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

    /**
     * 获得字典类型列表
     */
    @ResponseBody
    @RequestMapping(value = "getDictTypeList")
    public Resp getDictTypeList(){
        return Resp.success(null, dictService.getDictTypeList());
    }

}
