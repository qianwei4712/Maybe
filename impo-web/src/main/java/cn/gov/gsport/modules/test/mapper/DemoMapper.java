package cn.gov.gsport.modules.test.mapper;

import cn.gov.gsport.core.base.BaseMapper;
import cn.gov.gsport.modules.test.entity.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author shiva   2019/12/15 22:09
 */
@Mapper
@Component
public interface DemoMapper extends BaseMapper<Demo> {
}
