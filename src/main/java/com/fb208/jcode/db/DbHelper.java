package com.fb208.jcode.db;

import com.zaxxer.hikari.HikariDataSource;

public class DbHelper {
    /**
    *参考链接：https://www.jianshu.com/p/dbcd7a6dde98?utm_campaign=maleskine&utm_content=note&utm_medium=writer_share&utm_source=weibo
    *
    *   String sqlserverUrl ="jdbc:sqlserver://localhost:1433;DatabaseName=数据库名称";
        String sqlserverDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";

        String mySqlUrl="jdbc:mysql://localhost:3307/数据库名称";
        String mySqlDriver="com.mysql.cj.jdbc.Driver";

        String oracleUrl="jdbc:oracle:thin:@//localhost:1521/数据库名称";
        String oracleDriver="oracle.jdbc.OracleDriver";

        //orcale也可以使用sid连接的url
        String oracleUrl="jdbc:oracle:thin:@localhost:1521:SID";
        *
        *
    * */
    public HikariDataSource customDataSource(String uri, String drive, String userName, String pwd) {
        final HikariDataSource customDataConfig = new HikariDataSource();

        customDataConfig.setJdbcUrl(uri);
        customDataConfig.setDriverClassName(drive);


        customDataConfig.setUsername(userName);
        customDataConfig.setPassword(pwd);

        customDataConfig.setMaximumPoolSize(2);
        customDataConfig.setMinimumIdle(1);
        customDataConfig.setAutoCommit(true);
        //customDataConfig.setConnectionTestQuery("SELECT 1 FROM DUAL");
        customDataConfig.addDataSourceProperty("cachePrepStmts", "true");
        customDataConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        customDataConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return customDataConfig;
    }


}
