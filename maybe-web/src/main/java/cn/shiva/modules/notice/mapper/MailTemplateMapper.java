package cn.shiva.modules.notice.mapper;

import cn.shiva.core.base.BaseMapper;
import cn.shiva.modules.notice.entity.MailTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 邮件模板Mapper
* @author shiva  2020-01-10
*/
@Mapper
@Component
public interface MailTemplateMapper extends BaseMapper<MailTemplate> {

    /**
     * @param param 查询参数，no内日期字符yyMMdd
     * @return 获得当天最新流水号
     */
    public String getLatestNo(String param);

}