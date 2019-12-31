package cn.shiva.system.service;

import cn.shiva.common.lang.StringUtils;
import cn.shiva.core.base.BaseService;
import cn.shiva.core.utils.SysUtils;
import cn.shiva.system.entity.Dict;
import cn.shiva.system.mapper.DictMapper;
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

    @Override
    public boolean saveOrUpdate(Dict dict) {
        boolean result = super.saveOrUpdate(dict);
        SysUtils.clearDictCache();
        return result;
    }

    /**
     * 获得字典类型列表
     */
    public List<String> getDictTypeList() {
        return dictMapper.getDictTypeList();
    }

    /**
     * 根据字典类型查询字典数据信息
     * @param type 字典类型
     * @return 字典列表
     */
    public List<Dict> getType(String type) {
        return SysUtils.getDictList(type);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * @param type 字典类型
     * @param value 字典键值
     * @param defaultValue 默认值
     * @return 字典标签
     */
    public String getLabel(String type, String value, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
            for (Dict dict : SysUtils.getDictList(type)){
                if (type.equals(dict.getType()) && value.equals(dict.getValue())){
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

}
