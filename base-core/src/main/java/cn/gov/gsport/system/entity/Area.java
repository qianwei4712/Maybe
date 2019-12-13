package cn.gov.gsport.system.entity;

import cn.gov.gsport.core.base.BaseEntity;
import lombok.Data;

/**
 * @author shiva   2019/12/12 22:51
 */
@Data
public class Area extends BaseEntity<Area> {

    /**
     * 名称
     */
    private String name;

    /**
     * 上级id
     */
    private Long pid;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 地区等级
     */
    private Integer levelType;

    /**
     * 全称，从中国开始
     */
    private String mergeName;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 拼音
     */
    private String phoneticize;

}
