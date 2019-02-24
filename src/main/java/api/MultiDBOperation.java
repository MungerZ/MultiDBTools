package api;

import common.DBType;
import dbHandler.DBUtil;
import exception.StringToEnumException;

import java.sql.Connection;

public class MultiDBOperation {

    public Connection getConnection(String dbType, String dbName, String ip, String username, String password, String port){

        DBType enDBType = null;
        try{
            enDBType = DBType.valueOf(dbType);
        }catch(StringToEnumException ex){
            ex.printStackTrace();
            ex.printExceptionInfo();
            // TODO print log
        }

        String jdbcUrl = DBUtil.concatJdbcUrl(enDBType, ip, port, dbName);
        Connection connection = DBUtil.getConnection(jdbcUrl, username, password);
        if (null == connection){
            // TODO print log 获取数据库连接
            return null;
        }

        return connection;
    }
}
