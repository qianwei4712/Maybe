package cn.gov.gsport.modules.test.service;

import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.modules.test.entity.Demo;
import cn.gov.gsport.modules.test.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shiva   2019/12/15 22:19
 */
@Service
public class DemoService extends BaseService<Demo, DemoMapper> {

    @Autowired
    private DemoMapper demoMapper;

}
