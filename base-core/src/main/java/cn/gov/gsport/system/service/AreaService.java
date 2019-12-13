package cn.gov.gsport.system.service;

import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.system.entity.Area;
import cn.gov.gsport.system.mapper.AreaMapper;
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
