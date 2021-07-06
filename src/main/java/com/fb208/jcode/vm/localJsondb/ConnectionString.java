package com.fb208.jcode.vm.localJsondb;

import lombok.Data;

import java.util.List;

@Data
public class ConnectionString {
    List<DbItem> mssql;
    List<DbItem> mysql;

    @Data
    public class DbItem{
        String serverName;
        String serverType;
        String url;
        String driver;
        String userName;
        String password;
        Boolean isUse;
    }
}


