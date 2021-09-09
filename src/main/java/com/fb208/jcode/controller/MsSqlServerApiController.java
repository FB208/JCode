package com.fb208.jcode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fb208.jcode.mapper.MsSqlServerMapper;
import com.fb208.jcode.service.DbApiService;
import com.fb208.jcode.service.JdbcService;
import com.fb208.jcode.util.*;
import com.fb208.jcode.vm.Option;
import com.fb208.jcode.vm.localJsondb.ConnectionString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/mssql")
public class MsSqlServerApiController {

    @Value("${jcode.filepath.resources}")
    String resourcePath;


    @Autowired
    MsSqlServerMapper msSqlServerMapper;
    @Autowired
    JdbcService jdbcService;
    @Autowired
    DbApiService dbApiService;

    /**
     * 链接数据库
     *
     * @param option
     * @param conn
     * @return
     */
    @PostMapping(value = "/conn")
    public JSONObject mssqlConn(@RequestBody Option option, String conn) {
        String path = resourcePath + "\\localJsondb\\connectionString.json";
        String jsonStr = JsonFileTool.getDatafromFile(path);
        ConnectionString connModel = JSON.parseObject(jsonStr, ConnectionString.class);
        List<ConnectionString.DbItem> dblist = connModel.getMssql();
        //获取到选中的链接字符串
        ConnectionString.DbItem selectConn =
                dblist.stream().filter(m -> m.getServerName().equals(conn)).findFirst().get();
        jdbcService.init(selectConn);
        JdbcTemplate jdbcTemplate = jdbcService.getJdbcTemplate();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(msSqlServerMapper.selectDbName());
        List<String> dbList = list.stream().map(m -> m.get("Name")).map(String::valueOf).collect(Collectors.toList());
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
    public JSONObject mssqlOpenDb(@RequestBody Option option, String dbName) {
        JdbcTemplate jdbcTemplate = jdbcService.getJdbcTemplate();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(msSqlServerMapper.selectTableName(dbName));
        List<String> tableNameList =
                list.stream().map(m -> m.get("name")).map(String::valueOf).collect(Collectors.toList());
        JSONObject resultJson = new JSONObject();
        resultJson.put("result1", tableNameList);
        return resultJson;
    }

    /**
     * mssqlserver生成实体
     *
     * @param tableName
     * @return
     */
    @PostMapping(value = "/doEntity")
    public JSONObject mssqlDoEntity(@RequestBody Option option, String dbName, String tableName) {
        JSONObject resultJson = new JSONObject();
        String result = dbApiService.doEntity(option, dbName, tableName);
        resultJson.put("result1", result.toString());

        return resultJson;
    }

    /**
     * 批量生成
     *
     * @param dbName
     * @return
     */
    @PostMapping(value = "/batch")
    public JSONObject mssqlBatch(@RequestBody Option option, String dbName) throws Exception {
        JSONObject resultJson = new JSONObject();
        JdbcTemplate jdbcTemplate = jdbcService.getJdbcTemplate();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(msSqlServerMapper.selectTableName(dbName));
        List<String> tableNameList =
                list.stream().map(m -> m.get("name")).map(String::valueOf).collect(Collectors.toList());
        String uuid = UUID.randomUUID().toString();
        String tempPath =
                resourcePath + "\\temp\\" + uuid;
        FileTool.createDir(tempPath);
        FileTool.createDir(tempPath + "\\entity");
        FileTool.createDir(tempPath + "\\mapper\\sqlProvider");
        for(int i=0;i<tableNameList.size();i++)
        {
            String tableName = tableNameList.get(i);
            String entity = dbApiService.doEntity(option, dbName, tableName);
            Map<String, String> mapper = dbApiService.doMapper(option, dbName, tableName);
            if (mapper.get("success").equals("false")) {
                if (!option.getErrorSkip()) {//判断跳过还是返回错误
                    resultJson.put("success",mapper.get("success"));
                    resultJson.put("msg",mapper.get("msg"));
                    return resultJson;
                }
            }
            else{
                try {
                    FileTool.StringBufferWrite(tempPath + "\\entity\\" + tableName + ".java", entity);
                    FileTool.StringBufferWrite(tempPath + "\\mapper\\" + tableName + "Mapper.java", mapper.get("mapper"));
                    FileTool.StringBufferWrite(tempPath + "\\mapper\\sqlProvider\\" + tableName + "MapperSqlProvider.java", mapper.get("sqlProvider"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        ZipTool.generateFile(tempPath, "rar");



        resultJson.put("path","temp/"+uuid+".rar");

        return resultJson;
    }

    /**
     * mssqlserver生成mapper
     *
     * @param tableName
     * @return
     */
    @PostMapping(value = "/doMapper")
    @ResponseBody
    public JSONObject mssqlDoMapper(@RequestBody Option option, String dbName, String tableName) {

        JSONObject resultJson = new JSONObject();
        Map<String, String> result = dbApiService.doMapper(option, dbName, tableName);

        resultJson.put("success",result.get("success"));
        resultJson.put("msg",result.get("msg"));
        resultJson.put("result1", result.get("mapper"));
        resultJson.put("result2", result.get("sqlProvider"));

        return resultJson;
    }
}
