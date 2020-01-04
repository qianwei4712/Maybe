package cn.shiva.modules.overall.web;

import cn.shiva.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shiva   2020/1/3 23:54
 */
@Controller
@RequestMapping(value = "${adminPath}/overall")
public class OverallController extends BaseController {

    @RequestMapping(value = "console")
    public String console(){
        return "modules/overall/console";
    }

}
