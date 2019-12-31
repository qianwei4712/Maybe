package cn.shiva.system.service;

import cn.shiva.core.base.BaseService;
import cn.shiva.system.entity.Area;
import cn.shiva.system.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shiva   2019/12/12 23:06
 */
@Service
public class AreaService extends BaseService<Area, AreaMapper> {

    @Autowired
    private  AreaMapper areaMapper;

    /**
     * 根据上级地区id获取下级地区列表
     * @param id 上级地区id
     */
    public List<Area> getListByPid(Long id) {
        return  areaMapper.getListByPid(id);
    }

}
