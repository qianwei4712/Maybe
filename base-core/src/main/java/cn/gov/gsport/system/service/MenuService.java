package cn.gov.gsport.system.service;

import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.system.entity.Menu;
import cn.gov.gsport.system.mapper.MenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shiva   2019/8/7 20:17
 */
@Service
public class MenuService extends BaseService<Menu, MenuMapper> {


    @Transactional(readOnly = false)
    public void saveMenu(Menu menu) {
        //获取上级节点
//        Menu parent = getById(menu.getPid());
//        if (parent == null){
//            menu.setPid(-1);
//            menu.setPids("-1,");
//        }else {
//            // 设置新的父节点串
//            menu.setPids(parent.getPids() + parent.getId()+",");
//        }

        //保存或更新
//        saveOrUpdate(menu);

        //TODO 更新子节点 parentIds
//        List<Menu> list = menuMapper.findByPidsArray(menu.getId());
//        for (Menu e : list){
//            e.setPids(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
//            menuMapper.updateParentIds(e);
//        }
//        // 清除用户菜单缓存
//        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);

    }
}
