package cn.gov.gsport.system.service;

import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.system.entity.Log;
import cn.gov.gsport.system.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* 系统日志服务
* @author shiva  2019-12-23
*/
@Service
public class LogService extends BaseService<Log, LogMapper> {

    @Autowired
    private LogMapper logMapper;

}
