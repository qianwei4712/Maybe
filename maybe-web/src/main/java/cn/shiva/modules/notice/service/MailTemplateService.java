package cn.shiva.modules.notice.service;

import cn.shiva.common.lang.DateUtils;
import cn.shiva.common.lang.StringUtils;
import cn.shiva.core.base.BaseService;
import cn.shiva.core.constant.BaseConstant;
import cn.shiva.modules.notice.entity.MailTemplate;
import cn.shiva.modules.notice.mapper.MailTemplateMapper;
import cn.shiva.modules.overall.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* 邮件模板服务
* @author shiva  2020-01-10
*/
@Service
public class MailTemplateService extends BaseService<MailTemplate, MailTemplateMapper> {

    /**
     * 模板编号前缀前缀
     */
    private static final String PRE_FIX_STR = "MSG";
    /**
     * 流水号
     */
    private static Integer SERIAL_NUM = -1;
    /**
     * 日期字符串
     */
    private static String YY_MM_DD = "";

    @Autowired
    private MailTemplateMapper mailTemplateMapper;
    @Autowired
    private MailServiceImpl mailService;


    /**
     * 模板发送控制器
     * @param mailTemplate 邮件发送模板
     * @param address 收件地址数组
     * @param properties 参数字符串 例如 person:许乐;age:24
     */
    public void mailTemplateSend(MailTemplate mailTemplate, String[] address, String properties) throws Exception {
        //对模板的发送内容，进行参数替换
        replaceMailByProperites(mailTemplate, properties);
        //判断模板类型，进行发送
        switch (mailTemplate.getMsgType()){
            case 0:
                for (String s : address) {
                    mailService.sendSimpleMail(s, mailTemplate.getSubject(), mailTemplate.getContent());
                }
                break;
            case 1:
                for (String s : address) {
                    mailService.sendHtmlMail(s, mailTemplate.getSubject(), mailTemplate.getContent());
                }
                break;
            case 2:
                for (String s : address) {
                    mailService.sendAttachmentsMail(s, mailTemplate.getSubject(), mailTemplate.getContent(), mailTemplate.getRscPath());
                }
                break;
            default: break;
        }
    }




    /**
     * 为模板生成编号
     */
    public void judgeNoIsExist(MailTemplate mailTemplate){
        if (mailTemplate != null && StringUtils.isBlank(mailTemplate.getNo())){
            mailTemplate.setNo(seriaNumGenerator());
        }
    }


    /**
     * 每日凌晨，定时器调用重置流水号参数
     */
    public void resetSerialParams(){
        SERIAL_NUM = 0;
        YY_MM_DD = DateUtils.formatDate(new Date(), "yyMMdd");
    }

    /////////////////*********************************/////////////////
    /////////////////           private 方法           /////////////////
    /////////////////*********************************/////////////////

    /**
     * 根据参数，对模板进行参数替换。
     */
    private void replaceMailByProperites(MailTemplate mailTemplate, String properties){
        //处理参数，进行替换
        List<String[]> propertiesList = new ArrayList<>();
        if (StringUtils.isNotBlank(properties)){
            String[] properties_kv = properties.split(BaseConstant.SPLIT_SYMBOL_SEMICOLON);
            for (String s : properties_kv) {
                //根据：分割,最多2个字符串
                String[] kv = s.split(BaseConstant.SPLIT_SYMBOL_COLON, 2);
                if (kv.length > 1) {
                    //若确实根据：分割了，那个加入到列表
                    propertiesList.add(kv);
                }
            }
        }
        //第一个循环结束后，存在符合规范的键值对
        if (propertiesList.size() > 0){
            String content = mailTemplate.getContent();
            for (String[] item : propertiesList ) {
                content = content.replace("${" + item[0] + "}", item[1]);
            }
            mailTemplate.setContent(content);
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

}
