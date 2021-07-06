package com.fb208.jcode.util;

public class DbTypeTool {
    public static String dbtypeTojavaType(String dbType) {
        String javaType = "";
        switch (dbType) {
            case "nvarchar":
            case "varchar": {
                javaType = "String";
            }
            break;
            case "tinyint":
            case "smallint": {
                javaType = "short";
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
                javaType = "BigDecimal";
            }
            break;
            case "float": {
                javaType = "Double";
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
