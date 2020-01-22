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

}