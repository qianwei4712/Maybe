package core;


import cn.shiva.common.lang.StringUtils;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shiva   2019/12/16 15:16
 */
public class FreemarkerInit {

    private static String diskPathBase = "";
    private static Map<String, Object> dataMap = new HashMap<>();

    /**
     * 实际生成模板方法
     */
    public void init(Map<String, String> model, ResultSet resultSet){
        try {
            dataMap = getDataMap(resultSet);
            diskPathBase = model.get("filePath");

            generateXml(model, resultSet);
            generateEntity(model, resultSet);
            generateController(model);
            generateService(model);
            generateMapper(model);
            generateViewList(model);
            generateViewForm(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成xml
     */
    private void generateXml(Map<String, String> model, ResultSet resultSet) throws Exception{
        final String file = model.get("ClassName") + "Mapper.xml";
        String diskPath = diskPathBase;
        creatFolder(diskPath);
        final String path = diskPath + file;
        final String templateName = "xml.ftl";
        File mapperFile = new File(path);
        generateFileByTemplate(templateName, mapperFile, model, dataMap);
    }

    /**
     * 生成entity
     */
    private void generateEntity(Map<String, String> model, ResultSet resultSet) throws Exception{
        final String file = model.get("ClassName") + ".java";
        String diskPath = diskPathBase;
        diskPath +=  "entity\\";
        creatFolder(diskPath);
        final String path = diskPath + file;
        final String templateName = "entity.ftl";
        File mapperFile = new File(path);
        generateFileByTemplate(templateName, mapperFile, model, dataMap);
    }

    /**
     *  生成Controller
     */
    private void generateController(Map<String, String> model) throws Exception{
        final String file = model.get("ClassName") + "Controller.java";
        String diskPath = diskPathBase;
        diskPath +=  "controller\\";
        creatFolder(diskPath);
        final String path = diskPath + file;
        final String template = "controller.ftl";
        File mapperFile = new File(path);
        generateFileByTemplate(template, mapperFile, model);
    }

    /**
     *  生成Service
     */
    private void generateService(Map<String, String> model) throws Exception{
        final String file = model.get("ClassName") + "Service.java";
        String diskPath = diskPathBase;
        diskPath +=  "service\\";
        creatFolder(diskPath);
        final String path = diskPath + file;
        final String template = "service.ftl";
        File mapperFile = new File(path);
        generateFileByTemplate(template, mapperFile, model);
    }

    /**
     *  生成Mapper
     */
    private void generateMapper(Map<String, String> model) throws Exception{
        final String file = model.get("ClassName") + "Mapper.java";
        String diskPath = diskPathBase;
        diskPath +=  "mapper\\";
        creatFolder(diskPath);
        final String path = diskPath + file;
        final String template = "mapper.ftl";
        File mapperFile = new File(path);
        generateFileByTemplate(template, mapperFile, model);
    }

    /**
     *  生成列表页面
     */
    private void generateViewList(Map<String, String> model) throws Exception{
        final String file = model.get("className") + "List.html";
        String diskPath = diskPathBase;
        diskPath +=  model.get("moduleName") + "\\";
        creatFolder(diskPath);
        final String path = diskPath + file;
        final String template = "viewList.ftl";
        File mapperFile = new File(path);
        generateFileByTemplate(template, mapperFile, model);
    }

    /**
     *  生成表单页面
     */
    private void generateViewForm(Map<String, String> model) throws Exception{
        final String file = model.get("className") + "Form.html";
        String diskPath = diskPathBase;
        diskPath +=  model.get("moduleName") + "\\";
        creatFolder(diskPath);
        final String path = diskPath + file;
        final String template = "viewForm.ftl";
        File mapperFile = new File(path);
        generateFileByTemplate(template, mapperFile, model);
    }

    private Map<String, Object> getDataMap(ResultSet resultSet) throws SQLException {
        List<ColumnClass> columnClassList = new ArrayList<>();
        ColumnClass columnClass = null;
        while(resultSet.next()){
            //id字段略过
            if("id".equals(resultSet.getString("COLUMN_NAME")) || "del_flag".equals(resultSet.getString("COLUMN_NAME"))
                    || "create_date".equals(resultSet.getString("COLUMN_NAME")) || "create_by".equals(resultSet.getString("COLUMN_NAME"))
                    || "remarks".equals(resultSet.getString("COLUMN_NAME"))) {
                continue;
            }
            columnClass = new ColumnClass();
            //获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            //转换字段名称，如 sys_name 变成 SysName
            columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
            //字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnClass);
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("columnClass", columnClassList);
        return dataMap;
    }

    private void creatFolder(String filePath) {
        File folder = new File(filePath);
        if (!folder.exists() && !folder.isDirectory()) {
            System.out.println("文件夹路径不存在，创建路径:" + filePath);
            folder.mkdirs();
        }
    }


    private void generateFileByTemplate(final String templateName, File file, Map<String, String> dataMap) throws Exception{
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8),10240);
        template.process(dataMap, out);
    }

    private void generateFileByTemplate(final String templateName, File file, Map<String, String> model, Map<String, Object> dataMap) throws Exception{
        dataMap.putAll(model);
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8),10240);
        template.process(dataMap, out);
    }


    private String replaceUnderLineAndUpperCase(String str){
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        int count = sb.indexOf("_");
        while(count!=0){
            int num = sb.indexOf("_",count);
            count = num + 1;
            if(num != -1){
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count , count + 1,ia + "");
            }
        }
        String result = sb.toString().replaceAll("_","");
        return StringUtils.capitalize(result);
    }

}
