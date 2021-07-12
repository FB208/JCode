package com.fb208.jcode.service;

import com.alibaba.fastjson.JSONObject;
import com.fb208.jcode.mapper.DBMapper;
import com.fb208.jcode.util.DbTypeTool;
import com.fb208.jcode.util.NameTool;
import com.fb208.jcode.util.StringTool;
import com.fb208.jcode.vm.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DbApiService {
    @Autowired
    JdbcService jdbcService;
    @Autowired
    DBMapper dbMapper;

    public String doEntity(Option option, String dbName, String tableName) {
        JdbcTemplate jdbcTemplate = jdbcService.getJdbcTemplate();
        StringBuilder result = new StringBuilder();


        List<Map<String, Object>> list = jdbcTemplate.queryForList(dbMapper.selectTableColumn(dbName, tableName));
        result.append("import lombok.Data; \n").append("import java.util.Date; \n").append("\n");
        result.append(" @Data \n").append(" public class " + tableName + " \n").append(" { \n");
        list.forEach(item -> {
            //类型
            result.append("    ").append(DbTypeTool.dbtypeTojavaType(item.get("Type").toString()));
            //属性
            result.append(" ").append(NameTool.lineToHump(item.get("ColumnName").toString())).append(";");
            //注释
            result.append("  //").append(item.get("Remark")).append("\n");
        }); result.append(" } \n");
        return result.toString();
    }

    public Map<String,String> doMapper(Option option, String dbName, String tableName)
    {
        Map<String,String> map = new HashMap<>();
        JdbcTemplate jdbcTemplate = jdbcService.getJdbcTemplate();
        StringBuilder result = new StringBuilder();
        StringBuilder sqlProvider = new StringBuilder();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(dbMapper.selectTableColumn(dbName,tableName));
        //异常校验
        long pkCount = list.stream().filter(m->m.get("IsPK").equals("1")).count();
        if(pkCount!=1)
        {
            map.put("success","false");
            map.put("msg","目前仅支持单主键表，异常表："+tableName);
            return map;
        }
        result.append("import org.apache.ibatis.annotations.*; \n").append("\n");
        result.append("import java.util.List; \n").append("\n");
        result.append("import com.haiot.fas.entity."+tableName+"; \n").append("\n");//namespace
        result.append("import com.haiot.fas.mapper.sqlProvider."+tableName+"MapperSqlProvider; \n").append("\n");//namespace
        result.append(" @Mapper \n").append(" public interface " + tableName + "Mapper ").append(" { \n");
        //所有字段
        String columns = list.stream().map(m->m.get("ColumnName")).map(String::valueOf).collect(Collectors.joining(","));
        //#{columnName},所有字段
        String columns_sharp = list.stream().map(m->"#{"+NameTool.lineToHump(m.get("ColumnName").toString())+"}").map(String::valueOf).collect(Collectors.joining(","));

        //所有字段，不包含主键
        String columnsNotPK = list.stream().filter(m->m.get("IsPK").equals("0")).map(m->m.get("ColumnName")).map(String::valueOf).collect(Collectors.joining(","));
        //#{columnName},不包含主键
        String columnsNotPK_sharp = list.stream().filter(m->m.get("IsPK").equals("0")).map(m->"#{"+NameTool.lineToHump(m.get("ColumnName").toString())+"}").map(String::valueOf).collect(Collectors.joining(","));

        //主键(数据库中原来的样子，一般是下划线命名user_name）
        String columnPK = list.stream().filter(m->m.get("IsPK").equals("1")).map(m->m.get("ColumnName")).map(String::valueOf).findFirst().get();
        //#{主键}
        String columnPK_sharp = "#{"+NameTool.lineToHump(columnPK)+"}";

        //主键类型 java
        String typePK = DbTypeTool.dbtypeTojavaType(list.stream().filter(m->m.get("IsPK").equals("1")).map(m->m.get("Type")).map(String::valueOf).findFirst().get());

        //所有字段，不包含自增
        String columnsNotIdentity = list.stream().filter(m->m.get("IsIdentity").equals("0")).map(m->m.get("ColumnName")).map(String::valueOf).collect(Collectors.joining(","));
        //#{columnName},不包含自增
        String columnsNotIdentity_sharp = list.stream().filter(m->m.get("IsIdentity").equals("0")).map(m->"#{"+NameTool.lineToHump(m.get("ColumnName").toString())+"}").map(String::valueOf).collect(Collectors.joining(","));



        //新增
        result.append("    ").append("@Insert(\"insert into ["+tableName+"] ("+columnsNotIdentity+") values("+columnsNotIdentity_sharp+")\")").append("\n")
                .append("    ").append("int insert("+tableName+" "+ NameTool.firstCharToLowerCase(tableName) +");");
        result.append("\n\n");
        //修改
        result.append("    ").append("@Update(\"update ["+tableName+"] set ");
        String updateInner="";
        for(int i=0;i<list.size();i++)
        {
            //主键和自增序列不可修改
            if (list.get(i).get("IsPK").equals("1")||list.get(i).get("IsIdentity").equals("1")) {
                continue;
            }
            updateInner+=list.get(i).get("ColumnName")+"="+"#{"+NameTool.lineToHump(list.get(i).get("ColumnName").toString())+"},";
        }
        if (updateInner.length()>0) {
            updateInner= StringTool.trimEnd(updateInner,",");
        }
        result.append(updateInner);
        result.append(" where ").append(columnPK).append("=").append(columnPK_sharp).append("\")").append("\n")
                .append("    ").append("int update(").append(tableName).append(" ").append(NameTool.firstCharToLowerCase(tableName)).append(");");
        result.append("\n\n");
        //删除
        result.append("    ").append("@Delete(\"delete from ["+tableName+"] where "+columnPK+" = "+columnPK_sharp+" \")").append("\n")
                .append("    ").append("int delete(@Param(\""+ NameTool.lineToHump(columnPK)+"\")").append(typePK).append(" ").append(columnPK).append(");");
        result.append("\n\n");

        //查询全部
        result.append("    ").append("@Select(\"select "+columns+" from ["+tableName+"]\")").append("\n")
                .append("    ").append("List<"+tableName+">").append(" ").append("selectAll();");
        result.append("\n\n");

        //主键查询
        result.append("    ").append("@Select(\"select "+columns+" from ["+tableName+"] where "+columnPK+" = "+columnPK_sharp+"\")").append("\n")
                .append("    ").append(tableName).append(" ").append("selectOne(@Param(\""+NameTool.lineToHump(columnPK)+"\")"+typePK+" "+NameTool.lineToHump(columnPK)+");");
        result.append("\n\n");

        //判断是否要加SqlProvider
        if (option.getIsSqlProvider()) {
            result.append("    ").append("@SelectProvider(type = "+tableName+"MapperSqlProvider.class, method = \"selectQuery\")").append("\n")
                    .append("    ").append("List<"+tableName+"> selectQuery(@Param(\""+NameTool.firstCharToLowerCase(tableName)+"\") "+tableName+" "+NameTool.firstCharToLowerCase(tableName))
                    .append(",").append("@Param(\"orderColumn\")String orderColumn").append(");").append("\n");
            result.append("    ").append("@SelectProvider(type = "+tableName+"MapperSqlProvider.class, method = \"selectQueryCount\")").append("\n")
                    .append("    ").append("int selectQueryCount(@Param(\""+NameTool.firstCharToLowerCase(tableName)+"\") "+tableName+" "+NameTool.firstCharToLowerCase(tableName))
                    .append(",").append("@Param(\"orderColumn\")String orderColumn").append(");").append("\n");
            result.append("    ").append("/**").append("\n")
                    .append("    ").append("* pageIndex：页码，从1开始；pageSize：每页X条").append("\n")
                    .append("    ").append("*/").append("\n");
            result.append("    ").append("@SelectProvider(type = "+tableName+"MapperSqlProvider.class, method = \"selectQueryByPage\")").append("\n")
                    .append("    ").append("List<"+tableName+"> selectQueryByPage(@Param(\""+NameTool.firstCharToLowerCase(tableName)+"\") "+tableName+" "+NameTool.firstCharToLowerCase(tableName))
                    .append(",").append("@Param(\"orderColumn\")String orderColumn")
                    .append(",").append("@Param(\"pageIndex\")Integer pageIndex")
                    .append(",").append("@Param(\"pageSize\")Integer pageSize").append(");").append("\n");

            sqlProvider.append("import cn.hutool.core.util.StrUtil;").append("\n");
            sqlProvider.append("import org.apache.ibatis.annotations.Param;").append("\n");
            sqlProvider.append("import org.apache.ibatis.jdbc.SQL;").append("\n");
            sqlProvider.append("import com.haiot.fas.constant.CommonConstant;").append("\n");//namespace
            sqlProvider.append("import com.haiot.fas.entity."+tableName+";").append("\n");//namespace
            sqlProvider.append("public class "+tableName+"MapperSqlProvider {").append("\n");
            sqlProvider.append("    ").append("public String selectQuery(@Param(\""+NameTool.firstCharToLowerCase(tableName)+"\") "+tableName+" "+NameTool.firstCharToLowerCase(tableName)+", @Param(\"orderColumn\")String orderColumn){").append("\n")
                    .append("    ").append("    ").append("return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM,"+NameTool.firstCharToLowerCase(tableName)+",orderColumn,0,0).toString();").append("\n")
                    .append("    ").append("}").append("\n");
            sqlProvider.append("    ").append("public String selectQueryCount(@Param(\""+NameTool.firstCharToLowerCase(tableName)+"\") "+tableName+" "+NameTool.firstCharToLowerCase(tableName)+", @Param(\"orderColumn\")String orderColumn){").append("\n")
                    .append("    ").append("    ").append("return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COUNT,"+NameTool.firstCharToLowerCase(tableName)+",orderColumn,0,0).toString();").append("\n")
                    .append("    ").append("}").append("\n");
            sqlProvider.append("    ").append("public String selectQueryByPage(@Param(\""+NameTool.firstCharToLowerCase(tableName)+"\") "+tableName+" "+NameTool.firstCharToLowerCase(tableName)+", @Param(\"orderColumn\")String orderColumn, @Param(\"pageIndex\")Integer pageIndex, @Param(\"pageSize\")Integer pageSize")
                    .append("){\n")
                    .append("    ").append("    ").append("return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM_BY_PAGE,"+NameTool.firstCharToLowerCase(tableName)+",orderColumn,pageIndex,pageSize).toString();").append("\n")
                    .append("    ").append("}").append("\n");
            sqlProvider.append("    ").append("private SQL generatorQuerySql(CommonConstant.SQL_BASIC_TYPE sqlBasicType,@Param" +
                    "(\""+tableName+"\") "+tableName+" "+NameTool.firstCharToLowerCase(tableName)+",@Param(\"orderColumn\")String orderColumn,@Param(\"pageIndex\")Integer " +
                    "pageIndex,@Param(\"pageSize\")Integer pageSize){").append("\n");
            sqlProvider.append("    ").append("    ").append("SQL selectSql = new SQL(){{").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("switch (sqlBasicType){").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("case SELECT_COLUNM:{").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("    ").append("SELECT" +"(\""+columns+"\");").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("    ").append("ORDER_BY(orderColumn);").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("}break;").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("case SELECT_COLUNM_BY_PAGE:{").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("    ").append("SELECT" +"(\""+columns+"\");").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("    ").append("ORDER_BY(orderColumn);").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("    ").append("OFFSET((pageIndex-1)*pageSize);").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("    ").append("FETCH_FIRST_ROWS_ONLY(pageSize);").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("}break;").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("case SELECT_COUNT:{").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("    ").append("SELECT(\"count(1)\");").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("    ").append("}break;").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("}").append("\n");
            sqlProvider.append("    ").append("    ").append("    ").append("FROM(\"["+tableName+"]\");").append("\n");
            for(int i=0;i<list.size();i++)
            {
                if(DbTypeTool.dbtypeTojavaType(list.get(i).get("Type").toString()).equals("String"))
                {
                    //字符串类型的判断isNotBlank
                    sqlProvider.append("    ").append("    ").append("    ").append("if (StrUtil.isNotBlank("+NameTool.firstCharToLowerCase(tableName)+".get"+NameTool.firstCharToUpperCase(NameTool.lineToHump(list.get(i).get("ColumnName").toString()))+"())) {").append("\n");
                }
                else{
                    //其他类型的判断
                    sqlProvider.append("    ").append("    ").append("    ").append("if ("+NameTool.firstCharToLowerCase(tableName)+".get"+NameTool.firstCharToUpperCase(NameTool.lineToHump(list.get(i).get("ColumnName").toString()))+"() != null) {").append("\n");
                }

                sqlProvider.append("    ").append("    ").append("    ").append("    ").append("WHERE(\""+list.get(i).get("ColumnName")+" = #{"+NameTool.firstCharToLowerCase(tableName)+"."+NameTool.lineToHump(list.get(i).get("ColumnName").toString())+"}\");").append("\n");
                sqlProvider.append("    ").append("    ").append("    ").append("}").append("\n");
            }
            sqlProvider.append("    ").append("    ").append("}};").append("\n");
            sqlProvider.append("    ").append("    ").append("return selectSql;").append("\n");
            sqlProvider.append("    ").append("}").append("\n");
            sqlProvider.append("}");

        }


        //结束
        result.append("}");
        map.put("success","true");
        map.put("mapper",result.toString());
        map.put("sqlProvider",sqlProvider.toString());
        return  map;
    }
}
