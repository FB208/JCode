package com.fb208.jcode;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.PreparedStatement;

public class TransactionalMain {
    public static void main(String[] args) {

        final HikariDataSource customDataConfig = new HikariDataSource();

        customDataConfig.setJdbcUrl("jdbc:mysql://39.98.138.165:3306/fas4?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC");
        customDataConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");


        customDataConfig.setUsername("root");
        customDataConfig.setPassword("QingHong123!@#");

        customDataConfig.setMaximumPoolSize(2);
        customDataConfig.setMinimumIdle(1);
        customDataConfig.setAutoCommit(false);
        //customDataConfig.setConnectionTestQuery("SELECT 1 FROM DUAL");
        customDataConfig.addDataSourceProperty("cachePrepStmts", "true");
        customDataConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        customDataConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        try {
            String sql = "UPDATE account SET balance=balance+? WHERE name=?";
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            pstmt.setDouble(1,balance);
//            pstmt.setString(2,name);
            //pstmt.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
