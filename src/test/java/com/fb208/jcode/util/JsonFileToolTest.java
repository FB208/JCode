package com.fb208.jcode.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fb208.jcode.vm.localJsondb.ConnectionString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFileToolTest {

    @Test
    public void read()
    {
        System.out.println(System.getProperty("user.dir"));
        String path=System.getProperty("user.dir")+"\\src\\main\\resources\\localJsondb\\connectionString.json";
        String jsonStr = JsonFileTool.getDatafromFile(path);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        ConnectionString connModel = JSON.parseObject(jsonStr,ConnectionString.class);
    }


}