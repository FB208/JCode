package com.fb208.jcode.controller;


import com.fb208.jcode.common.BaseController;
import com.fb208.jcode.db.DbHelper;
import com.fb208.jcode.vm.test;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/")
public class HomeController extends BaseController {
    @RequestMapping("/")
    public String index(ModelMap map) {
        test t = new test();
        t.setAge(10);
        t.setAge(12);
        t.setName("张三");
        t.setChilds(new ArrayList<String>() {
            {
                add("李四");
                add("王五");
            }
        });
        map.addAttribute("resource","123");
        map.addAttribute("test",t);
        return "index";
    }
    @RequestMapping("/db")
    public String db()
    {
        String url="jdbc:mysql://39.98.138.165:3306/smart_meter?useUnicode=true&characterEncoding=utf-8" +
                "&serverTimezone=UTC";
        String driver="com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String pwd = "QingHong123!@#";
        HikariDataSource dataSource = null;
        JdbcTemplate  jdbcTemplate;
        try {
            dataSource = new DbHelper().customDataSource(url,driver,userName,pwd);
            jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from keyword");
            int a = 1;
        } catch (Exception e) {

        }
        finally {
            dataSource.close();
        }

    return null;
    }
}
