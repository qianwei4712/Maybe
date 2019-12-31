package cn.shiva.system.service;

import cn.shiva.core.base.BaseService;
import cn.shiva.system.entity.Log;
import cn.shiva.system.mapper.LogMapper;
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
