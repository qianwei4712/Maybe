package cn.shiva.modules.notice.service;

import cn.shiva.common.lang.DateUtils;
import cn.shiva.common.lang.StringUtils;
import cn.shiva.core.base.BaseService;
import cn.shiva.modules.notice.entity.MailTemplate;
import cn.shiva.modules.notice.mapper.MailTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* 邮件模板服务
* @author shiva  2020-01-10
*/
@Service
public class MailTemplateService extends BaseService<MailTemplate, MailTemplateMapper> {

    //前缀
    private static final String PRE_FIX_STR = "MSG";
    //流水号
    private static Integer SERIAL_NUM = -1;
    //日期字符串
    private static String YY_MM_DD = "";

    @Autowired
    private MailTemplateMapper mailTemplateMapper;

    /**
     * 为模板生成编号
     */
    public void judgeNoIsExist(MailTemplate mailTemplate){
        if (mailTemplate != null && StringUtils.isBlank(mailTemplate.getNo())){
            mailTemplate.setNo(seriaNumGenerator());
        }
    }

    /**
     * 生成模板编号: MSG + YYMMDD + 三位流水号
     */
    private synchronized String seriaNumGenerator(){
        //判断是否重新启动服务
        if (SERIAL_NUM == -1){
            //当天日期字符串
            YY_MM_DD = DateUtils.formatDate(new Date(), "yyMMdd");
            //者服务重新启动后，需要数据库查询当天编号
            //获取当天最新编号
            String latestNo = mailTemplateMapper.getLatestNo(YY_MM_DD);
            //如果数值为空，表示需要从0开始计算
            if (StringUtils.isBlank(latestNo)){
                SERIAL_NUM = 0;
            }else {
                SERIAL_NUM = Integer.valueOf(latestNo);
            }
        }
        //拼接，integer转string，前面补0三位,先加1再拼接
        return StringUtils.appends(PRE_FIX_STR, YY_MM_DD, String.format("%03d", ++SERIAL_NUM));
    }

    /**
     * 每日凌晨，定时器调用重置流水号参数
     */
    public void resetSerialParams(){
        SERIAL_NUM = 0;
        YY_MM_DD = DateUtils.formatDate(new Date(), "yyMMdd");
    }




}
