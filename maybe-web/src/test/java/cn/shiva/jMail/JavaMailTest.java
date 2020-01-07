package cn.shiva.jMail;

import cn.shiva.modules.overall.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shiva   2020/1/7 19:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaMailTest {

    @Autowired
    MailService mailService;

    /**
     * 发送简单邮件
     */
    @Test
    public void simpleMailMessage(){
        mailService.sendSimpleMail("qianwei4712@163.com", "主题：简单邮件(QQ个人邮件)-抄送，密送测试", "测试邮件内容");
        System.out.println("发送成功！");
    }

}
