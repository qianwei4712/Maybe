package cn.shiva.system.mapper;

import cn.shiva.core.base.BaseMapper;
import cn.shiva.system.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author shiva   2019/12/12 23:01
 */
@Mapper
@Component
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 根据上级id查询下级地区列表
     * @param id 上级地区id
     * @return 下级地区列表，根据id排序
     */
    List<Area> getListByPid(Serializable id);
}
