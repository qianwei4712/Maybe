package cn.gov.gsport.system.entity;

import cn.gov.gsport.common.others.CommonUtils;
import cn.gov.gsport.core.base.BaseEntity;
import cn.gov.gsport.core.constant.BaseConstant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shiva   2019/7/6 20:29
 */
@Data
public class Role extends BaseEntity<Role> {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 英文名
     */
    private String enname;

    /**
     * 状态，0-正常，1-禁用
     */
    private String status;

    /**
     * 菜单ids,用","拼接
     */
    private String menuIds;

    /**
     * 拥有菜单列表
     */
    private List<Menu> menuList = new ArrayList<Menu>();

    /**
     * 初始化menuid字符串
     */
    public void menuIdsInit() {
        if (menuList.isEmpty()){
            return;
        }
        String ids = "";
        //遍历菜单列表
        for (Menu item: menuList) {
            ids = ids + item.getId() + BaseConstant.SPLIT_SYMBOL;
        }
        ids = CommonUtils.subEndComma(ids);
        this.menuIds = ids;

    }
}
