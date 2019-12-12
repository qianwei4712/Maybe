package cn.gov.gsport.system.service;

import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.core.basic.Resp;
import cn.gov.gsport.core.constant.BaseConstant;
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
    public Resp saveMenu(Menu menu) {
        //获取上级节点
        Menu parent = getById(menu.getPid());
        if (parent != null){
            if ( parent.getPids().split(BaseConstant.SPLIT_SYMBOL).length >= 3){
                return Resp.error("最多允许三级菜单！", null);
            }
            // 设置新的父节点串
            menu.setPids(parent.getPids() + parent.getId()+",");
        }else {
            menu.setPid(BaseConstant.SUPER_TREE_ID);
            menu.setPids(BaseConstant.SUPER_TREE_IDS);
        }

        //保存或更新
        saveOrUpdate(menu);

        return Resp.success("保存成功！", null);

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
