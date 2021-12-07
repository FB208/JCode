package com.fb208.jcode.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class MySqlMapper {

    public String selectDbName(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SCHEMA_NAME AS `database` FROM INFORMATION_SCHEMA.SCHEMATA;");
        return sql.toString();
    }
    public String selectTableName() {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from information_schema.tables where table_type = 'BASE TABLE' order by table_name");
        return sql.toString();
    }

    public String selectTableName(String dbName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from information_schema.tables where table_schema='"+dbName+"' and table_type = 'BASE TABLE' order by table_name");
        return sql.toString();
    }

    public String selectTableColumn(String dbName,String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT " +
                "TABLE_SCHEMA AS '库名'," +
                "TABLE_NAME AS '表名'," +
                "COLUMN_NAME AS '列名'," +
                "ORDINAL_POSITION AS '列的排列顺序'," +
                "COLUMN_DEFAULT AS '默认值'," +
                "IS_NULLABLE AS '是否为空'," +
                "DATA_TYPE AS '数据类型'," +
                "CHARACTER_MAXIMUM_LENGTH AS '字符最大长度'," +
                "NUMERIC_PRECISION AS '数值精度(最大位数)'," +
                "NUMERIC_SCALE AS '小数精度'," +
                "COLUMN_TYPE AS '列类型'," +
                "COLUMN_KEY 'KEY'," +
                "EXTRA AS '额外说明'," +
                "COLUMN_COMMENT AS '注释' " +
                "FROM information_schema.`COLUMNS` " +
                "WHERE TABLE_SCHEMA = '"+dbName+"' " +
                "AND TABLE_NAME = '"+tableName+"' " +
                "ORDER BY TABLE_NAME, ORDINAL_POSITION;");

        return sql.toString();
    }
}
