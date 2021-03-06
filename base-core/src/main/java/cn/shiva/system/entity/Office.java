package cn.shiva.system.entity;

import cn.shiva.core.base.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shiva   2019/7/1 22:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Office extends TreeEntity<Office> {

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 部门等级。0-顶级
     */
    private Integer grade;

    /**
     * 部门座机
     */
    private String telephone;


}
