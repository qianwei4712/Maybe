package cn.gov.gsport.system.mapper;

import cn.gov.gsport.core.base.BaseMapper;
import cn.gov.gsport.system.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 系统日志Mapper
* @author shiva  2019-12-23
*/
@Mapper
@Component
public interface LogMapper extends BaseMapper<Log> {

}