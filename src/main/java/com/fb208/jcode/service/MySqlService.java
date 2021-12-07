package com.fb208.jcode.service;

import com.fb208.jcode.mapper.MySqlMapper;
import com.fb208.jcode.util.DbTypeTool;
import com.fb208.jcode.util.NameTool;
import com.fb208.jcode.vm.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MySqlService {
    @Autowired
    JdbcService jdbcService;

    @Autowired
    MySqlMapper mySqlMapper;

    /**
     * 生成实体
     * @param option
     * @param dbName
     * @param tableName
     * @return
     */
    public String doEntity(Option option, String dbName, String tableName) {
        JdbcTemplate jdbcTemplate = jdbcService.getJdbcTemplate();
        StringBuilder result = new StringBuilder();


        List<Map<String, Object>> list = jdbcTemplate.queryForList(mySqlMapper.selectTableColumn(dbName, tableName));
        result.append("import lombok.Data; \n").append("import java.util.Date; \n").append("\n");
        result.append(" @Data \n").append(" public class " + tableName + " \n").append(" { \n");
        list.forEach(item -> {
            //类型
            result.append("    ").append(DbTypeTool.dbtypeTojavaType(item.get("数据类型").toString()));
            //属性
            result.append(" ").append(NameTool.lineToHump(item.get("列名").toString())).append(";");
            //注释
            result.append("  //").append(item.get("注释")).append("\n");
        }); result.append(" } \n");
        return result.toString();
    }
}
