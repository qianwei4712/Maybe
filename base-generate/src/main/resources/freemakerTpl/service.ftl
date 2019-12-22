package ${packageName}.${moduleName}.service;

import cn.gov.gsport.core.base.BaseService;
import ${packageName}.${moduleName}.entity.${ClassName};
import ${packageName}.${moduleName}.mapper.${ClassName}Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* ${functionName}服务
* @author ${classAuthor}  ${createTime}
*/
@Service
public class ${ClassName}Service extends BaseService<${ClassName}, ${ClassName}Mapper> {

    @Autowired
    private ${ClassName}Mapper ${className}Mapper;

}
