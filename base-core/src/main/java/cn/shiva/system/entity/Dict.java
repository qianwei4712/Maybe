package cn.shiva.system.entity;

import cn.shiva.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shiva   2019/12/14 18:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Dict extends BaseEntity<Dict> {

    /**
     * 类型
     */
    private String type;

    /**
     * 数据值
     */
    private String value;

    /**
     * 标签名
     */
    private String label;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private Integer sort;

}
