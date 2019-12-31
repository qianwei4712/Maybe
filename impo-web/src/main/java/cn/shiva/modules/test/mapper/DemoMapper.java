package cn.shiva.modules.test.mapper;

import cn.shiva.core.base.BaseMapper;
import cn.shiva.modules.test.entity.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author shiva   2019/12/15 22:09
 */
@Mapper
@Component
public interface DemoMapper extends BaseMapper<Demo> {
}
