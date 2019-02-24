import api.MultiDBOperation;
import dbHandler.DBUtil;

import java.sql.Connection;
import java.util.List;

public class Demo {
    public static void main(String[] args){
        testGetTableNames();
    }

    // 测试获取表外键
    public static void testGetImportedKeysInfo(){
        MultiDBOperation multiDBOperation = new MultiDBOperation();
        Connection conn = multiDBOperation.getConnection("MySQL", "mungerTest01", "192.168.43.167", "root", "Munger_123", "3306");
        List<String> ImportedKeysList = DBUtil.getImportedKeys(conn, "deal_integral");
        for (String importedKey: ImportedKeysList){
            System.out.println(importedKey);
        }
    }

    //获取表主键
    public static void testGetPrimaryKey(){
        MultiDBOperation multiDBOperation = new MultiDBOperation();
        Connection conn = multiDBOperation.getConnection("MySQL", "mungerTest01", "192.168.43.167", "root", "Munger_123", "3306");
        List<String> primaryKeyList = DBUtil.getPrimaryKey(conn, null);
        for (String primaryKey: primaryKeyList){
            System.out.println(primaryKey);
        }
    }

    //测试获取表列名信息
    public static void testGetTableFieldsInfo(){
        MultiDBOperation multiDBOperation = new MultiDBOperation();
        Connection conn = multiDBOperation.getConnection("MySQL", "mungerTest01", "192.168.43.167", "root", "Munger_123", "3306");
        //List<String> columnsInfo = DBUtil.getFieldNameAndType(conn, "student"); // 获取指定数据库指定表列信息
        List<String> columnsInfo = DBUtil.getFieldNameAndType(conn,null); // 获取指定数据库所有表列信息
        for (String columnInfo: columnsInfo){
            System.out.println(columnInfo);
        }
    }


    // 获取数据库连接对应数据库的表名信息
    public static void testGetTableNames(){
        MultiDBOperation multiDBOperation = new MultiDBOperation();
        Connection conn = multiDBOperation.getConnection("MySQL", "mungerTest01", "192.168.43.167", "root", "Munger_123", "3306");
        List<String> tableNameList = DBUtil.getTableNames(conn);
        for (String tableName: tableNameList){
            System.out.println(tableName);
        }
    }

}
