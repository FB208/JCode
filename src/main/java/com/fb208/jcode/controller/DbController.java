package com.fb208.jcode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fb208.jcode.db.DbHelper;
import com.fb208.jcode.entity.User;
import com.fb208.jcode.mapper.DBMapper;
import com.fb208.jcode.util.*;
import com.fb208.jcode.vm.Option;
import com.fb208.jcode.vm.localJsondb.ConnectionString;

import com.fb208.jcode.vm.test;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/db")
@Slf4j
public class DbController {
    @Value("${jcode.filepath.resources}")
    String resourcePath;
    @Autowired
    DBMapper dbMapper;

    @GetMapping(value = "/connMng")
    public String connectionStringManager(ModelMap map) {

        return "index";
    }

    @GetMapping(value = "/mssql")
    public String mssql(ModelMap map) {
        String path=resourcePath +"\\localJsondb\\connectionString.json";
        String jsonStr = JsonFileTool.getDatafromFile(path);
        ConnectionString connModel = JSON.parseObject(jsonStr,ConnectionString.class);
        map.addAttribute("connList", connModel.getMssql());

        List<ConnectionString.DbItem> dblist = connModel.getMssql();
        ConnectionString.DbItem defaultDb= dblist.stream().filter(m->m.getIsUse()).findFirst().get();

        String url = defaultDb.getUrl();
        String driver = defaultDb.getDriver();
        String userName = defaultDb.getUserName();
        String pwd = defaultDb.getPassword();
        HikariDataSource dataSource = null;
        JdbcTemplate jdbcTemplate;
        try {
            dataSource = new DbHelper().customDataSource(url, driver, userName, pwd);
            jdbcTemplate = new JdbcTemplate(dataSource);

            List<Map<String, Object>> list = jdbcTemplate.queryForList(dbMapper.selectTableName());
            List<String> tableNameList = new ArrayList<>();
            list.forEach(item -> {
                tableNameList.add(item.get("name").toString());
            });
            map.addAttribute("tableNameList", tableNameList);
            int a = 1;
        } catch (Exception e) {

        } finally {
            dataSource.close();
        }

        return "/db/mssql/index";
    }


}
