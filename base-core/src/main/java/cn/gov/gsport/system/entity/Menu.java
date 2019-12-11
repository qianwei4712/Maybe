package cn.gov.gsport.system.entity;

import cn.gov.gsport.core.base.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author shiva   2019/7/6 20:44
 */
@Data
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Menu extends TreeEntity<Menu> {

    /**
     * 菜单名
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 链接地址
     */
    private String href;

    /**
     * 小图标
     */
    private String icon;

    /**
     * 是否在菜单中显示,false-不显示，true-显示
     */
    private Boolean isShow;

    /**
     * 权限标识
     */
    private String permission;


}
