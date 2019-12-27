package cn.gov.gsport.system.entity;

import cn.gov.gsport.common.others.CommonUtils;
import cn.gov.gsport.core.base.BaseEntity;
import cn.gov.gsport.core.constant.BaseConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiva   2019/7/1 22:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class User extends BaseEntity<User> {

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门
     */
    private Office office;

    /**
     * 部门id,字符串拼接
     */
    private String officeIds;

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码，加密后
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 座机
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别，0-男，1-女，2-其他
     */
    private String sex;

    /**
     * 头像链接
     */
    private String photo;

    /**
     * 状态，0-启用，1-禁用
     */
    private String status;

    /**
     * 角色ids,用","拼接
     */
    private String roleIds;

    /**
     * 一个用户具有多个角色
     */
    private List<Role> roleList = new ArrayList();

    /**
     * roleIds
     */
    public void roleIdsInit() {
        if (roleList.isEmpty()){
            return;
        }
        String ids = "";
        //遍历菜单列表
        for (Role item: roleList) {
            ids = ids + item.getId() + BaseConstant.SPLIT_SYMBOL;
        }
        ids = CommonUtils.subEndComma(ids);
        this.roleIds = ids;

    }

}
