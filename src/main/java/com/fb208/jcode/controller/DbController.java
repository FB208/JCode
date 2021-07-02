package com.fb208.jcode.controller;

import com.fb208.jcode.db.DbHelper;
import com.fb208.jcode.mapper.DBMapper;
import com.fb208.jcode.util.DbTypeTool;
import com.fb208.jcode.util.NameTool;
import com.fb208.jcode.vm.test;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/db")
@Slf4j
public class DbController {

    @Autowired
    DBMapper dbMapper;

    @GetMapping(value="/connMng")
    public String connectionStringManager(ModelMap map) {

        return "index";
    }

    @GetMapping(value="/mssql")
    public String mssql(ModelMap map) {
        String url="jdbc:sqlserver://39.98.138.165:1433;databasename=dmcloud_OAuth";
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String userName = "sa";
        String pwd = "Yang123!@#";
        HikariDataSource dataSource = null;
        JdbcTemplate jdbcTemplate;
        try {
            dataSource = new DbHelper().customDataSource(url,driver,userName,pwd);
            jdbcTemplate = new JdbcTemplate(dataSource);

            List<Map<String,Object>> list = jdbcTemplate.queryForList(dbMapper.selectTableName());
            List<String> tableNameList = new ArrayList<>();
            list.forEach(item->{
                tableNameList.add(item.get("name").toString());
            });
            map.addAttribute("tableNameList",tableNameList);
            int a = 1;
        } catch (Exception e) {

        }
        finally {
            dataSource.close();
        }

        return "/db/mssql/index";
    }

    @PostMapping(value = "/mssql/doEntity")
    @ResponseBody
    public String mssqlDoEntity(String tableName){
        String url="jdbc:sqlserver://39.98.138.165:1433;databasename=dmcloud_OAuth";
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String userName = "sa";
        String pwd = "Yang123!@#";
        HikariDataSource dataSource = null;
        JdbcTemplate jdbcTemplate;
        StringBuilder vue = new StringBuilder();
        try {
            dataSource = new DbHelper().customDataSource(url,driver,userName,pwd);
            jdbcTemplate = new JdbcTemplate(dataSource);

            List<Map<String,Object>> list = jdbcTemplate.queryForList(dbMapper.selectTableColumn(tableName));

            vue.append(" @Data \n")
                    .append(" public class "+tableName+" \n")
                    .append(" { \n");
            list.forEach(item->{
                vue.append(DbTypeTool.dbtypeTojavaType(item.get("Type").toString())).append(" ").append(item.get("ColumnName")).append(";").append("\n");
            });
            vue.append(" } \n");
            int a = 1;
        } catch (Exception e) {

        }
        finally {
            dataSource.close();
        }
        return  vue.toString();
    }

}
