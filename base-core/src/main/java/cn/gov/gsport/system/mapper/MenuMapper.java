package cn.gov.gsport.system.mapper;

import cn.gov.gsport.core.base.BaseMapper;
import cn.gov.gsport.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shiva   2019/7/6 21:14
 */
@Mapper
@Component
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据角色ID获得菜单列表
     * @param id 主键ID
     * @return 菜单列表
     */
    List<Menu> selectMenuListByRoleId(@Param("id") Long id);
}
