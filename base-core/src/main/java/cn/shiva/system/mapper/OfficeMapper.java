package cn.shiva.system.mapper;

import cn.shiva.core.base.BaseMapper;
import cn.shiva.system.entity.Office;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author shiva   2019/7/6 12:07
 */
@Mapper
@Component
public interface OfficeMapper extends BaseMapper<Office> {

}
