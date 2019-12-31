package cn.shiva.system.controller;

import cn.shiva.core.base.BaseController;
import cn.shiva.core.basic.Page;
import cn.shiva.core.basic.Resp;
import cn.shiva.core.utils.LogUtils;
import cn.shiva.system.entity.Area;
import cn.shiva.system.service.AreaService;
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
 * @author shiva   2019/12/12 23:14
 */
@Controller
@RequestMapping(value = "${adminPath}/area")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    @ModelAttribute
    public Area get(@RequestParam(required=false) Long id) {
        if (id != null){
            return areaService.getById(id);
        }else{
            return new Area();
        }
    }


    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "system/areaList";
    }


    /**
     * 分页请求列表界面数据
     */
    @ResponseBody
    @RequestMapping(value = "findByPage")
    public Resp findByPage(HttpServletRequest request, HttpServletResponse response, Area area){
        try {
            Page<Area> page = areaService.findByPage(request, response, area);
            return Resp.success(null, page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.logging(request, null, e);
        }
        return Resp.error();
    }

    /**
     * 编辑界面
     */
    @RequestMapping(value = "form")
    public String form(Area area, Model model){
        model.addAttribute("area",area);
        return "system/areaForm";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "save")
    public String save(Area area, RedirectAttributes model) {
        try {
            areaService.saveOrUpdate(area);
            model.addFlashAttribute("resMsg", RESP_MSG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("resMsg", RESP_MSG_ERROR);
        }
        return "redirect:" + adminPath + "/area";
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public Resp delete(Long id) {
        try {
            if (areaService.deleteLogic(id)){
                return Resp.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

    /**
     * 根据上级id获取下级地区列表
     */
    @ResponseBody
    @RequestMapping(value = "getListByPid")
    public Resp getListByPid(Long pid) {
        try {
            List<Area> list = areaService.getListByPid(pid);
            return Resp.success(null, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

}
