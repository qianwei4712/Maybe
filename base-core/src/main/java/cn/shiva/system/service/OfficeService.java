package cn.shiva.system.service;

import cn.shiva.core.base.BaseService;
import cn.shiva.core.constant.BaseConstant;
import cn.shiva.core.utils.SysUtils;
import cn.shiva.system.entity.Office;
import cn.shiva.system.mapper.OfficeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shiva   2019/7/3 21:48
 */
@Service
public class OfficeService extends BaseService<Office, OfficeMapper> {

    @Transactional(readOnly = false)
    public void saveOffice(Office office) {
        //获取上级节点
        Office parent = getById(office.getPid());
        if (parent != null){
            // 设置新的父节点串
            office.setPids(parent.getPids() + parent.getId()+",");
        }else {
            office.setPid(BaseConstant.SUPER_TREE_ID);
            office.setPids(BaseConstant.SUPER_TREE_IDS);
        }

        saveOrUpdate(office);
        SysUtils.clearOfficeCache();
    }
}
