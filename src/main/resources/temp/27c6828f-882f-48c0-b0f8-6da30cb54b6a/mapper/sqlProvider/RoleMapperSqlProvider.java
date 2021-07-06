import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
public class RoleMapperSqlProvider {
    public String selectQuery(@Param("role") Role role, @Param("orderColumn")String orderColumn){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM,role,orderColumn,0,0).toString();
    }
    public String selectQueryCount(@Param("role") Role role, @Param("orderColumn")String orderColumn){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COUNT,role,orderColumn,0,0).toString();
    }
    public String selectQueryByPage(@Param("role") Role role, @Param("orderColumn")String orderColumn, @Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM_BY_PAGE,role,orderColumn,pageIndex,pageSize).toString();
    }
    private SQL generatorQuerySql(CommonConstant.SQL_BASIC_TYPE sqlBasicType,@Param("Role") Role role,@Param("orderColumn")String orderColumn,@Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize){
        SQL selectSql = new SQL(){{
            switch (sqlBasicType){
                case SELECT_COLUNM:{
                    SELECT("uuid,role_name");
                    ORDER_BY(orderColumn);
                }break;
                case SELECT_COLUNM_BY_PAGE:{
                    SELECT("uuid,role_name");
                    ORDER_BY(orderColumn);
                    OFFSET((pageIndex-1)*pageSize);
                    FETCH_FIRST_ROWS_ONLY(pageSize);
                }break;
                case SELECT_COUNT:{
                    SELECT("count(1)");
                }break;
            }
            FROM("[Role]");
            if (StrUtil.isNotBlank(role.getUuid())) {
                WHERE("uuid = #{role.uuid}");
            }
            if (StrUtil.isNotBlank(role.getRoleName())) {
                WHERE("role_name = #{role.roleName}");
            }
        }};
        return selectSql;
    }
}