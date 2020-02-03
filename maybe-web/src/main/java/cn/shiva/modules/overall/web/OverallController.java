package cn.shiva.modules.overall.web;

import cn.shiva.common.io.FileUtil;
import cn.shiva.core.base.BaseController;
import cn.shiva.core.basic.Resp;
import cn.shiva.core.utils.LogUtils;
import cn.shiva.core.utils.properties.PropUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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


    /**
     * 上传文件，并返回链接
     */
    @ResponseBody
    @RequestMapping(value = "uploadFile")
    public Resp uploadFile(HttpServletRequest request, MultipartFile file){
        try {
            if (file.isEmpty()){
                return Resp.error("文件不存在", null);
            }
            String filePath = FileUtil.uploadFile(request, file, PropUtil.get("maybe.uploadFilePath"));
            return Resp.success(null, filePath);
        } catch (Exception e) {
            LogUtils.exceptionCatch(request, e);
            return RESP_MSG_ERROR;
        }
    }


}
