package cn.shiva.modules.timer;

import cn.shiva.core.utils.SpringContextHolder;
import cn.shiva.modules.notice.service.MailTemplateService;
import cn.shiva.system.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 重置定时器，重置参数以及一些配置
 * @author shiva   2020/2/2 18:46
 */
@Configuration
@EnableScheduling
public class ResetTimer {

    @Autowired
    private MailTemplateService mailTemplateService;

    /**
     * 每天执行一次，进行配置参数的重置以及清零
     */
    @Scheduled(cron = "0 0 0 * * ?")
    private void resetDaily() {
        try {
            mailTemplateService.resetSerialParams();
            System.out.println("每日重置邮件流水号");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("每日重置出错");
        }
    }
}
