package com.fb208.jcode.entity;

import lombok.Data;

@Data
public class TableColumn {
    String TableName;
    String ShowIndex;
    String ColumnName;
    String IsIdentity;
    String IsPK;
    String TYpe;
    String Type;
    String DBSize;
    String Length;
    String Precision;
    String CanBeNull;
    String DefaultValue;
    String Remark;
}

