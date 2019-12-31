package cn.shiva.core.base;

import cn.shiva.common.lang.StringUtils;
import cn.shiva.core.basic.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * @author shiva   2019/6/4 20:24
 */
@Transactional(readOnly = true)
public class BaseService<T extends BaseEntity<T>, M extends BaseMapper<T>>{

    /**
     * 持久层对象
     */
    @Autowired
    protected M mapper;

    /**
     * 根据ID获取对象
     */
    public T getById(Serializable id){
        return mapper.selectById(id);
    }

    @Transactional(readOnly = false)
    public boolean saveOrUpdate(T t){
        Integer result;
        if (t.getId() == null){
            //新增
            t.preInsert();
            result = mapper.insert(t);
        }else {
            //更新
            result = mapper.updateById(t);
        }
        return 1 == result;
    }

    /**
     * 根据主键ID逻辑删除
     */
    @Transactional(readOnly = false)
    public boolean deleteLogic(Serializable id){
        Integer result = mapper.deleteLogic(id);
        return 1 == result;
    }

    /**
     * 条件查询，分页查询
     */
    public Page<T> findByPage(HttpServletRequest request, HttpServletResponse response, T t){
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isNumeric(pageNo) && StringUtils.isNumeric(pageSize)){
            PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        }else {
            return new Page<T>();
        }
        List<T> list = mapper.findList(t);
        return new Page<T>(list);
    }

    /**
     * 查询全部
     */
    public List<T> findAll() {
        return mapper.findAll();
    }

    /**
     * 根据id更改为指定状态
     */
    @Transactional(readOnly = false)
    public boolean changeStatus(Long id, String status) {
        try {
            Integer result = mapper.changeStatus(id, status);
            return 1 == result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
