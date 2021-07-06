package com.fb208.jcode.mapper;

import com.fb208.jcode.entity.TableColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBMapper {
    public String selectDbName(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT Name FROM Master..SysDatabases ORDER BY Name");
        return sql.toString();
    }
    public String selectTableName() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM SYSOBJECTS WHERE TYPE='U' ORDER BY name");
        return sql.toString();
    }
    public String selectTableName(String dbName) {
        StringBuilder sql = new StringBuilder();
        sql.append("USE "+dbName+";SELECT * FROM SYSOBJECTS WHERE TYPE='U' ORDER BY name;");
        return sql.toString();
    }

    public String selectTableColumn(String dbName,String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT cast(d.name as varchar(100)) TableName, " +
                " cast(a.colorder as varchar(100)) ShowIndex,cast(a.name as varchar(100)) ColumnName, " +
                " cast((case when COLUMNPROPERTY( a.id,a.name,'IsIdentity')=1 then '1'else '0' end) as varchar(100)) IsIdentity, " +
                " cast((case when (SELECT count(*) FROM sysobjects " +
                " WHERE (name in (SELECT name FROM sysindexes " +
                " WHERE (id = a.id) AND (indid in " +
                " (SELECT indid FROM sysindexkeys " +
                " WHERE (id = a.id) AND (colid in " +
                " (SELECT colid FROM syscolumns WHERE (id = a.id) AND (name = a.name))))))) " +
                " AND (xtype = 'PK'))>0 then '1' else '0' end) as varchar(100)) IsPK,cast(b.name as varchar(100)) Type ,cast(a.length as varchar(100)) DBSize, " +
                " cast(COLUMNPROPERTY(a.id,a.name,'PRECISION') as varchar(100)) as Lenth, " +
                " cast(isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0) as varchar(100)) as Precision,cast((case when a.isnullable=1 then '1'else '0' end) as varchar(100)) CanBeNull, " +
                " cast(isnull(e.text,'') as varchar(100)) DefaultValue,cast(isnull(g.[value], ' ') as varchar(100)) AS [Remark] " +
                " FROM  syscolumns a " +
                " left join systypes b on a.xtype=b.xusertype " +
                " inner join sysobjects d on a.id=d.id and d.xtype='U' and d.name<>'dtproperties' " +
                " left join syscomments e on a.cdefault=e.id " +
                " left join sys.extended_properties g on a.id=g.major_id AND a.colid=g.minor_id and g.name='MS_Description'" +
                " left join sys.extended_properties f on d.id=f.class and f.minor_id=0 " +
                " where b.name is not null " +
                " and d.name = '"+tableName+"' " +
                " order by a.id,a.colorder ");

        return sql.toString();
    }
}