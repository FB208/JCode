package com.fb208.jcode.service;

import com.fb208.jcode.db.DbHelper;
import com.fb208.jcode.vm.localJsondb.ConnectionString;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JdbcService {
    private HikariDataSource dataSource=null;
    private JdbcTemplate jdbcTemplate;
    public void init(ConnectionString.DbItem conn)
    {
        if(dataSource!=null){
            close();
        }
        String url = conn.getUrl();
        String driver = conn.getDriver();
        String userName = conn.getUserName();
        String pwd = conn.getPassword();
        dataSource = null;
        dataSource = new DbHelper().customDataSource(url, driver, userName, pwd);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }

    public void close(){
        dataSource.close();
    }
}
