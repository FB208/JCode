package com.fb208.jcode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fb208.jcode.mapper.MsSqlServerMapper;
import com.fb208.jcode.mapper.MySqlMapper;
import com.fb208.jcode.service.DbApiService;
import com.fb208.jcode.service.JdbcService;
import com.fb208.jcode.service.MySqlService;
import com.fb208.jcode.util.JsonFileTool;
import com.fb208.jcode.vm.Option;
import com.fb208.jcode.vm.localJsondb.ConnectionString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/mysql")
public class MySqlApiController {

    @Value("${jcode.filepath.resources}")
    String resourcePath;


    @Autowired
    MySqlMapper mySqlMapper;
    @Autowired
    JdbcService jdbcService;
    @Autowired
    MySqlService mySqlService;
    /**
     * 链接数据库
     *
     * @param option
     * @param conn
     * @return
     */
    @PostMapping(value = "/conn")
    public JSONObject mysqlConn(@RequestBody Option option, String conn) {
        String path = resourcePath + "\\localJsondb\\connectionString.json";
        String jsonStr = JsonFileTool.getDatafromFile(path);
        ConnectionString connModel = JSON.parseObject(jsonStr, ConnectionString.class);
        List<ConnectionString.DbItem> dblist = connModel.getMysql();
        //获取到选中的链接字符串
        ConnectionString.DbItem selectConn =
                dblist.stream().filter(m -> m.getServerName().equals(conn)).findFirst().get();
        jdbcService.init(selectConn);
        JdbcTemplate jdbcTemplate = jdbcService.getJdbcTemplate();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(mySqlMapper.selectDbName());
        List<String> dbList = list.stream().map(m -> m.get("database")).map(String::valueOf).collect(Collectors.toList());
        JSONObject resultJson = new JSONObject();
        resultJson.put("result1", dbList);
        return resultJson;
    }

    /**
     * 打开选定数据表并读取表
     *
     * @param option
     * @param dbName
     * @return
     */
    @PostMapping(value = "/openDb")
    public JSONObject mysqlOpenDb(@RequestBody Option option, String dbName) {
        JdbcTemplate jdbcTemplate = jdbcService.getJdbcTemplate();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(mySqlMapper.selectTableName(dbName));
        List<String> tableNameList =
                list.stream().map(m -> m.get("TABLE_NAME")).map(String::valueOf).collect(Collectors.toList());
        JSONObject resultJson = new JSONObject();
        resultJson.put("result1", tableNameList);
        return resultJson;
    }

    /**
     * 生成实体
     *
     * @param tableName
     * @return
     */
    @PostMapping(value = "/doEntity")
    public JSONObject mysqlDoEntity(@RequestBody Option option, String dbName, String tableName) {
        JSONObject resultJson = new JSONObject();
        String result = mySqlService.doEntity(option, dbName, tableName);
        resultJson.put("result1", result.toString());

        return resultJson;
    }
}
