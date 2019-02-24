package dbHandler;

import common.CommonStr;
import common.DBType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    public static String concatJdbcUrl(DBType dbType, String ip, String port, String dbName){
        String jdbcUrl = null;
        switch (dbType){
            case MySQL:
                jdbcUrl = CommonStr.MYSQL_JDBC_URL_PREFIX + ip + ":" + port + "/" + dbName + CommonStr.MYSQL_JDBC_URL_EXTRA;
                return jdbcUrl;
            case Oracle:
                jdbcUrl = CommonStr.ORACLE_JDBC_URL_PREFIX + ip + ":" + port + CommonStr.ORACLE_JDBC_URL_EXTRA;
                return jdbcUrl;
            default:
                return null;
        }
    }

    public static Connection getConnection(String jdbcUrl, String username, String password){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO print log
            return null;
        }
        return conn;
    }

    /**
     * 获取数据库连接对应数据库中表的名字
     * @param conn
     * @return
     */
    public static List<String> getTableNames(Connection conn){
        String catalog = null;
        DatabaseMetaData databaseMetaData = null;
        List<String> tableNameList = new ArrayList<String>();
        try {
            catalog = conn.getCatalog();
            databaseMetaData = conn.getMetaData();
            ResultSet tableRet = databaseMetaData.getTables(catalog, "%","%",new String[]{"TABLE"});
            while(tableRet.next()){
                tableNameList.add(tableRet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNameList;
    }

    /**
     * 获取数据库连接对应数据库中表的列信息
     * @param conn
     * @param tableName if is null, get all field info.
     * @return
     */
    public static List<String> getFieldNameAndType(Connection conn, String tableName){
        List<String> resultList = new ArrayList<String>();
        String catalog = null;
        DatabaseMetaData databaseMetaData = null;
        String columnName = null;
        String columnType = null;
        try {
            catalog = conn.getCatalog();
            databaseMetaData = conn.getMetaData();
            if (null == tableName){
                tableName = "%";
            }
            ResultSet colRet = databaseMetaData.getColumns(catalog, "%",tableName,"%");
            while(colRet.next()){
                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                int dataSize = colRet.getInt("COLUMN_SIZE");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int nullable = colRet.getInt("NULLABLE");
                String resultStr = columnName+";"+columnType+";"+dataSize+";"+digits+";"+nullable;
                resultList.add(resultStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultList;
    }

    /**
     * 获取指定表的主键名称
     * @param conn
     * @param tableName
     * @return
     */
    public static List<String> getPrimaryKey(Connection conn, String tableName){
        List<String> resultList = new ArrayList<String>();
        String catalog = null;
        DatabaseMetaData databaseMetaData = null;
        String columnName = null;
        String columnType = null;
        try {
            catalog = conn.getCatalog();
            databaseMetaData = conn.getMetaData();
            ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(catalog,null,tableName);
            while(primaryKeyResultSet.next()){
                String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
                resultList.add(primaryKeyColumnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultList;
    }

    /**
     * 获取指定表外键信息
     * @param conn
     * @param tableName
     * @return
     */
    public static List<String> getImportedKeys(Connection conn, String tableName){
        List<String> resultList = new ArrayList<String>();
        String catalog = null;
        DatabaseMetaData databaseMetaData = null;
        String columnName = null;
        String columnType = null;
        ResultSet foreignKeyResultSet = null;
        try {
            catalog = conn.getCatalog();
            databaseMetaData = conn.getMetaData();
            foreignKeyResultSet = databaseMetaData.getImportedKeys(catalog,null,tableName);
            while(foreignKeyResultSet.next()){
                String fkColumnName = foreignKeyResultSet.getString("FKCOLUMN_NAME");
                String pkTableName = foreignKeyResultSet.getString("PKTABLE_NAME");
                String pkColumnName = foreignKeyResultSet.getString("PKCOLUMN_NAME");
                String resultStr = fkColumnName+";"+pkTableName+";"+pkColumnName;
                resultList.add(resultStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultList;
    }
}
