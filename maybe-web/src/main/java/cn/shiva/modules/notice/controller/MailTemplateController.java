package cn.shiva.modules.notice.controller;

import cn.shiva.common.lang.StringUtils;
import cn.shiva.core.base.BaseController;
import cn.shiva.core.basic.Page;
import cn.shiva.core.basic.Resp;
import cn.shiva.core.utils.LogUtils;
import cn.shiva.modules.notice.entity.MailTemplate;
import cn.shiva.modules.notice.service.MailTemplateService;
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
 * 邮件模板控制器
 * @author shiva  2020-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/notice/mailTemplate")
public class MailTemplateController extends BaseController {

    @Autowired
    private MailTemplateService mailTemplateService;

    @ModelAttribute
    public MailTemplate get(@RequestParam(required=false) Long id) {
        if (id != null){
            return mailTemplateService.getById(id);
        }else{
            return new MailTemplate();
        }
    }

    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "modules/notice/mailTemplateList";
    }

    @ResponseBody
    @RequestMapping(value = "findByPage")
    public Resp findByPage(HttpServletRequest request, HttpServletResponse response, MailTemplate mailTemplate){
        try {
            Page<MailTemplate> page = mailTemplateService.findByPage(request, response, mailTemplate);
            return Resp.success(null, page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.error();
    }

    @RequestMapping(value = "form")
    public String form(MailTemplate mailTemplate, Model model){
        mailTemplateService.judgeNoIsExist(mailTemplate);
        model.addAttribute("mailTemplate",mailTemplate);
        return "modules/notice/mailTemplateForm";
    }

    @RequestMapping(value = "save")
    public String save(MailTemplate mailTemplate, RedirectAttributes model, HttpServletRequest request) {
        try {
            mailTemplateService.saveOrUpdate(mailTemplate);
            model.addFlashAttribute("resMsg", RESP_MSG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.exceptionCatch(request, e);
            model.addFlashAttribute("resMsg", RESP_MSG_ERROR);
        }
        return "redirect:" + adminPath + "/notice/mailTemplate";
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public Resp delete(Long id, HttpServletRequest request) {
        try {
            if (mailTemplateService.deleteLogic(id)){
                return Resp.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.exceptionCatch(request, e);
        }
        return Resp.error();
    }

    /**
     * 填写收件箱以及发送页面
     */
    @RequestMapping(value = "mailSendAddressPage")
    public String mailSendAddressPage(Integer id, Model model){
        model.addAttribute("id", id);
        return "modules/notice/mailSendAddressPage";
    }


    /**
     * 模板发送控制器
     * @param mailTemplate 模板，传入id，ModelAttribute自动获取
     * @param address 收件地址
     */
    @ResponseBody
    @RequestMapping(value = "mailTemplateSend")
    public Resp mailTemplateSend(MailTemplate mailTemplate, String address){
        //TODO 发送邮件，先判断发送地址
//        mailTemplateService.mailTemplateSend(mailTemplate, address);
        return RESP_MSG_SUCCESS;
    }



}
