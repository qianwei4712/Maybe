package cn.gov.gsport.system.service;

import cn.gov.gsport.core.base.BaseService;
import cn.gov.gsport.core.constant.BaseConstant;
import cn.gov.gsport.core.utils.SysUtils;
import cn.gov.gsport.system.entity.Office;
import cn.gov.gsport.system.mapper.OfficeMapper;
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
