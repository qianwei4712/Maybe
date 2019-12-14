package cn.gov.gsport.system.service;

import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.system.entity.Dict;
import cn.gov.gsport.system.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shiva   2019/12/14 18:43
 */
@Service
public class DictService extends BaseService<Dict, DictMapper> {

    @Autowired
    private DictMapper dictMapper;

    /**
     * 获得字典类型列表
     */
    public List<String> getDictTypeList() {
        return dictMapper.getDictTypeList();
    }
}
