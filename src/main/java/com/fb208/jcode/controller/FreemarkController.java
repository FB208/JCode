package com.fb208.jcode.controller;

import com.fb208.jcode.db.DbHelper;
import com.fb208.jcode.util.NameTool;
import com.fb208.jcode.vm.test;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/freemark")
public class FreemarkController {
    @RequestMapping("index")
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

    @RequestMapping("/makeCode/vue")
    public String makeVue(ModelMap map) {

        String url="jdbc:mysql://39.98.138.165:3306/fas4?useUnicode=true&characterEncoding=utf-8" +
                "&serverTimezone=UTC";
        String driver="com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String pwd = "QingHong123!@#";
        HikariDataSource dataSource = null;
        JdbcTemplate jdbcTemplate;
        try {
            dataSource = new DbHelper().customDataSource(url,driver,userName,pwd);
            jdbcTemplate = new JdbcTemplate(dataSource);
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ")
                    .append(" COLUMN_NAME 列名, ")
                    .append(" COLUMN_TYPE 数据类型, ")
                    .append(" DATA_TYPE 字段类型, ")
                    .append(" CHARACTER_MAXIMUM_LENGTH 长度, ")
                    .append(" IS_NULLABLE 是否为空, ")
                    .append(" COLUMN_DEFAULT 默认值, ")
                    .append(" COLUMN_COMMENT 备注  ")
                    .append(" FROM ")
                    .append(" INFORMATION_SCHEMA.COLUMNS ")
                    .append(" Where ")
                    .append(" table_schema  = 'fas4' ")
                    .append(" and ")
                    .append(" table_name = 'building' ")
                    .append("  ");

            List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString());
            StringBuilder vue = new StringBuilder();
            list.forEach(item->{

                vue
                        .append("<el-col :span=\"12\" class=\"item\">\r\n")
                        .append("<span class=\"title\">"+item.get("备注")+"：</span>\n")
                        .append("<span class=\"content\">{{ building."+ NameTool.lineToHump(item.get("列名").toString())+
                                " " +
                                "}}</span>\n")
                        .append("</el-col>\n");

            });

            map.addAttribute("code",vue.toString());
            int a = 1;
        } catch (Exception e) {

        }
        finally {
            dataSource.close();
        }


        return "makeCode/vue";
    }
}
