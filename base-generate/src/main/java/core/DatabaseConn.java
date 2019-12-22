package core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Map;

/**
 * @author shiva   2019/12/16 17:05
 */
public class DatabaseConn {


    /**
     * 获得数据库结果集
     */
    public ResultSet getdbresultset(Map<String, String> map){
        try {
            Connection connection = getConnection(map);
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            return databaseMetaData.getColumns(map.get("dbName"),"%",  map.get("tableName"),"%");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得数据库连接
     */
    private Connection getConnection(Map<String, String> map) throws Exception{
        Class.forName(map.get("driver"));
        return DriverManager.getConnection(map.get("dbCoon"), map.get("user"), map.get("password"));
    }


}
