package ${packageName}.${moduleName}.entity;

import cn.gov.gsport.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* ${functionName}实体类
* @author ${classAuthor}  ${createTime}
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class ${ClassName} extends BaseEntity<${ClassName}> {

<#if columnClass?exists>
<#list columnClass as item>
    /**
     *${item.columnComment!}
     */
    <#if (item.columnType = 'VARCHAR' || item.columnType = 'CHAR' || item.columnType = 'TEXT')>
    private String ${item.changeColumnName?uncap_first};
    </#if>
    <#if item.columnType = 'TIMESTAMP' || item.columnType = 'DATE' || item.columnType = 'TIME' || item.columnType = 'DATETIME'>
    private Date ${item.changeColumnName?uncap_first};
    </#if>
    <#if item.columnType = 'BIGINT'>
    private Long ${item.changeColumnName?uncap_first};
    </#if>
    <#if item.columnType = 'BIT' || item.columnType = 'BOOLEAN' || item.columnType = 'TINYINT'>
    private Boolean ${item.changeColumnName?uncap_first};
    </#if>
    <#if item.columnType = 'INTEGER' || item.columnType = 'INT' || item.columnType = 'INT UNSIGNED'>
    private Integer ${item.changeColumnName?uncap_first};
    </#if>
    <#if item.columnType = 'FLOAT'>
    private FLOAT ${item.changeColumnName?uncap_first};
    </#if>
    <#if item.columnType = 'DOUBLE'>
    private DOUBLE ${item.changeColumnName?uncap_first};
    </#if>
    <#if item.columnType = 'BINARY'>
    private Byte ${item.changeColumnName?uncap_first};
    </#if>

</#list>
</#if>

}
