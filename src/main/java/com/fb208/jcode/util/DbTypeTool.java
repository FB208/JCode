package com.fb208.jcode.util;

public class DbTypeTool {
    public static String dbtypeTojavaType(String dbType){
        String javaType = "";
        switch (dbType)
        {
            case "varchar":javaType = "String";break;
            case "int":javaType="Integer";break;
            default:break;
        }
        return javaType;
    }
}
