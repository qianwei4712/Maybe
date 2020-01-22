import cn.shiva.common.lang.DateUtils;
import cn.shiva.common.lang.StringUtils;
import core.DatabaseConn;
import core.FreemarkerInit;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shiva   2019/12/15 15:08
 */
public class Generate {

    /**
     * 包名，例如：cn.shiva.modules
     */
    public static String packageName = "cn.shiva.modules";
    /**
     * 模块名，例：sys
     */
    public static String  moduleName = "notice";
    /**
     * 类名，例：User
     */
    public static String  className = "MailTemplate";
    /**
     * 模块名称，例：用户
     */
    public static String  functionName = "邮件模板";
    /**
     * 类作者，例：shiva
     */
    public static String  classAuthor = "shiva";
    /**
     * 数据库连接串
     */
    public static String dbCoon = "jdbc:mysql://127.0.0.1:3306/shiva-maybe?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";
    /**
     * 数据库名
     */
    public static String dbName = "shiva-maybe";
    /**
     * 数据库用户名
     */
    public static String user = "root";
    /**
     * 数据库密码
     */
    public static String password = "root";
    /**
     * 数据库连接驱动
     */
    public static String driver = "com.mysql.cj.jdbc.Driver";
    /**
     * 表明，例：sys_user
     */
    public static String  tableName = "notice_mail_template";
    /**
     * 表明，例：sys_user
     */
    public static String  filePath = "C:\\Users\\qianw\\Desktop\\1\\";


    //启动程序
    public static void main(String[] args) {
        try {
            Generate generate = new Generate();
            generate.generate(generate.getModel(), generate.dataBaseMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实际代码生成方法
     */
    public void generate(Map<String, String> model, Map<String, String> dataBaseMap){
        try {
            //数据库连接，获得结果集
            DatabaseConn db = new DatabaseConn();
            ResultSet resultSet = db.getdbresultset(dataBaseMap);
            //模板生成
            FreemarkerInit fi = new FreemarkerInit();
            fi.init(model, resultSet);

            System.out.println("代码创建成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /**
     * 包装Freemarker模板变量
     */
    public Map<String, String> getModel() {
        Map<String, String> model = new HashMap<>();
        model.put("packageName", StringUtils.lowerCase(packageName));
        model.put("moduleName", StringUtils.lowerCase(moduleName));
        model.put("className", StringUtils.uncapitalize(className));
        model.put("ClassName", StringUtils.capitalize(className));
        model.put("classAuthor", StringUtils.isNotBlank(classAuthor)?classAuthor:"Generate Shiva");
        model.put("functionName", functionName);
        model.put("urlPrefix", model.get("moduleName") + "/" + model.get("className"));
        model.put("viewPrefix", model.get("urlPrefix"));
        model.put("createTime", DateUtils.getDate());
        model.put("tableName", tableName);
        model.put("filePath", filePath);
        return model;
    }

    /**
     * 数据库连接信息
     */
    public Map<String, String> dataBaseMap() {
        Map<String, String> map = new HashMap<>();
        map.put("dbCoon", dbCoon);
        map.put("dbName", dbName);
        map.put("user", user);
        map.put("password", password);
        map.put("driver", driver);
        map.put("tableName", tableName);
        return map;
    }

}
