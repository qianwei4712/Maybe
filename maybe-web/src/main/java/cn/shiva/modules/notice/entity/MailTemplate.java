package cn.shiva.modules.notice.entity;

import cn.shiva.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 邮件模板实体类
* @author shiva  2020-01-10
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class MailTemplate extends BaseEntity<MailTemplate> {

    /**
     *模板编号
     */
    private String no;

    /**
     *模板发送类型，0-简单文本邮件，1-HTML邮件，2-带附件的邮件，3-静态资源的邮件
     */
    private Integer msgType;

    /**
     *模板名称
     */
    private String name;

    /**
     *模板内容
     */
    private String content;

    /**
     *附件路径
     */
    private String filepath;

    /**
     *资源路径，形式为name:path;name:path;
     */
    private String rscPath;

}
