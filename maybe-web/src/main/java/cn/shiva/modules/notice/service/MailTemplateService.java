package cn.shiva.modules.notice.service;

import cn.shiva.core.base.BaseService;
import cn.shiva.modules.notice.entity.MailTemplate;
import cn.shiva.modules.notice.mapper.MailTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* 邮件模板服务
* @author shiva  2020-01-10
*/
@Service
public class MailTemplateService extends BaseService<MailTemplate, MailTemplateMapper> {

    @Autowired
    private MailTemplateMapper mailTemplateMapper;

}
