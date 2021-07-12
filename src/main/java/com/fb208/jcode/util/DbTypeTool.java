package com.fb208.jcode.util;

public class DbTypeTool {
    public static String dbtypeTojavaType(String dbType) {
        String javaType = "";
        switch (dbType) {
            case "text":
            case "nchar":
            case "nvarchar":
            case "varchar": {
                javaType = "String";
            }
            break;
            case "tinyint":
            case "smallint": {
                javaType = "Short";
            }
            break;
            case "int": {
                javaType = "Integer";
            }
            break;
            case "bigint": {
                javaType = "Long";
            }
            break;
            case "smalldatetime":
            case "date":
            case "datetime2":
            case "datetime": {
                javaType = "Date";
            }
            break;
            case "time": {
                javaType = "Time";
            }
            break;
            case "numeric":
            case "decimal": {
                javaType = "Double";//""BigDecimal";
            }
            break;
            case "float": {
                javaType = "Double";
            }
            break;
            case "image": {
                javaType = "byte[]";
            }
            break;
            default: {
                javaType = dbType;
            }
            break;
        }
        return javaType;
    }
}
