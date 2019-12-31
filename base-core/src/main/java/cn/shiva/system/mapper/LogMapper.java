package cn.shiva.system.mapper;

import cn.shiva.core.base.BaseMapper;
import cn.shiva.system.entity.Log;
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