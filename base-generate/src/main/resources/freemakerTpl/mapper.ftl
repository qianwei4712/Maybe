package ${packageName}.${moduleName}.mapper;

import cn.gov.gsport.core.base.BaseMapper;
import ${packageName}.${moduleName}.entity.${ClassName};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* ${functionName}Mapper
* @author ${classAuthor}  ${createTime}
*/
@Mapper
@Component
public interface ${ClassName}Mapper extends BaseMapper<${ClassName}> {

}