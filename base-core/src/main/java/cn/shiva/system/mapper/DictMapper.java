package cn.shiva.system.mapper;

import cn.shiva.core.base.BaseMapper;
import cn.shiva.system.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shiva   2019/12/14 18:07
 */
@Mapper
@Component
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * @return 字典类型列表
     */
    List<String> getDictTypeList();
}
