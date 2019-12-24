<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "mybatis-3-mapper.dtd" >

<mapper namespace="${packageName}.${moduleName}.mapper.${ClassName}Mapper">

    <resultMap id="BaseResultMap" type="${packageName}.${moduleName}.entity.${ClassName}" >
        <id column="id" property="id" jdbcType="BIGINT" />
<#if columnClass?exists>
    <#list columnClass as item>
        <result column="${item.columnName}" property="${item.changeColumnName?uncap_first}" jdbcType="${item.columnType}" />
    </#list>
</#if>
        <result column="del_flag" property="delFlag" jdbcType="INTEGER" />
        <result column="create_date" property="createDate" jdbcType="TIMESTAMP" />
        <result column="create_by" property="createBy" jdbcType="BIGINT" />
        <result column="remarks" property="remarks" jdbcType="VARCHAR" />
    </resultMap>


    <insert id="insert" parameterType="${packageName}.${moduleName}.entity.${ClassName}" >
        insert into ${tableName} (id,
    <#if columnClass?exists>
        <#list columnClass as item>
            ${item.columnName},
        </#list>
    </#if>
            del_flag, create_date, create_by, remarks)
        values (${r"#{id,jdbcType=BIGINT}"},
    <#if columnClass?exists>
        <#list columnClass as item>
            ${r"#{"}${item.changeColumnName?uncap_first}${",jdbcType="}${item.columnType}${"}"},
        </#list>
    </#if>
            ${r"#{delFlag,jdbcType=INTEGER}, #{createDate,jdbcType=TIMESTAMP}, #{createBy,jdbcType=BIGINT}, #{remarks,jdbcType=VARCHAR}"})
    </insert>

    <!--根据ID更新-->
    <update id="updateById" parameterType="${packageName}.${moduleName}.entity.${ClassName}" >
        update ${tableName}
        set
    <#if columnClass?exists>
        <#list columnClass as item>
            ${item.columnName} = ${r"#{"}${item.changeColumnName?uncap_first}${",jdbcType="}${item.columnType}${"}"},
        </#list>
    </#if>
            del_flag = ${r"#{delFlag,jdbcType=INTEGER}"},
            create_date = ${r"#{createDate,jdbcType=TIMESTAMP}"},
            create_by = ${r"#{createBy,jdbcType=BIGINT}"},
            remarks = ${r"#{remarks,jdbcType=VARCHAR}"}
        where id = ${r"#{id,jdbcType=BIGINT}"}
    </update>


    <!--根据主键ID选择-->
    <select id="selectById" resultMap="BaseResultMap" parameterType="java.lang.Long">
        SELECT * FROM ${tableName} WHERE id = ${r"#{id}"}
    </select>


    <!--分页查询默认使用接口，条件查询语句-->
    <select id="findList" resultType="${packageName}.${moduleName}.entity.${ClassName}">
        SELECT *
        FROM ${tableName} opt
        <where>
            and opt.del_flag = ${r"#{DELFLAG_NORMAL}"}
        </where>
    </select>


    <!--根据主键ID逻辑删除-->
    <update id="deleteLogic">
        UPDATE ${tableName} SET del_flag = 1 WHERE id = ${r"#{id}"}
    </update>


</mapper>
