package cn.gov.gsport.core.constant;

/**
 * @author shiva   2019/12/05 22:54
 */
public class BaseConstant {

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "a123456.";

    /**
     * 拼接符号“,”
     */
    public static final String SPLIT_SYMBOL = ",";

    /**
     * 超级管理员ID
     */
    public static final Long SUPER_ADMIN_ID = 1L;

    /**
     * 顶级菜单（部门）ID，-1L
     */
    public static final Long SUPER_TREE_ID = -1L;

    /**
     * 顶级菜单（部门）ID，字符串拼接
     */
    public static final String SUPER_TREE_IDS = "-1,";

    /**
     * 拼接符号
     */
    public static final String SPLIT_SYMBOL_DASH = "-";

    /**
     * url上下文分割符号
     */
    public static final String URL_SYMBOL = "/";

    /**
     * save操作反馈信息字段
     */
    public static final String  RESMSG_SUCCESS = "操作成功！";

    /**
     * save操作反馈信息字段
     */
    public static final String  RESMSG_ERROR = "操作失败！";
}
