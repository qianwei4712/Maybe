package cn.gov.gsport.modules.test.controller;

import cn.gov.gsport.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shiva   2019/12/5 0:02
 */
@Controller
@RequestMapping(value = "${adminPath}/test")
public class TestController extends BaseController{

    @RequestMapping(value = "test")
    public String test(){
        return "modules/test/testList";
    }

}
