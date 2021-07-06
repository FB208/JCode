import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
public class R_User_RoleMapperSqlProvider {
    public String selectQuery(@Param("r_User_Role") R_User_Role r_User_Role, @Param("orderColumn")String orderColumn){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM,r_User_Role,orderColumn,0,0).toString();
    }
    public String selectQueryCount(@Param("r_User_Role") R_User_Role r_User_Role, @Param("orderColumn")String orderColumn){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COUNT,r_User_Role,orderColumn,0,0).toString();
    }
    public String selectQueryByPage(@Param("r_User_Role") R_User_Role r_User_Role, @Param("orderColumn")String orderColumn, @Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM_BY_PAGE,r_User_Role,orderColumn,pageIndex,pageSize).toString();
    }
    private SQL generatorQuerySql(CommonConstant.SQL_BASIC_TYPE sqlBasicType,@Param("R_User_Role") R_User_Role r_User_Role,@Param("orderColumn")String orderColumn,@Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize){
        SQL selectSql = new SQL(){{
            switch (sqlBasicType){
                case SELECT_COLUNM:{
                    SELECT("id,user_uuid,role_uuid");
                    ORDER_BY(orderColumn);
                }break;
                case SELECT_COLUNM_BY_PAGE:{
                    SELECT("id,user_uuid,role_uuid");
                    ORDER_BY(orderColumn);
                    OFFSET((pageIndex-1)*pageSize);
                    FETCH_FIRST_ROWS_ONLY(pageSize);
                }break;
                case SELECT_COUNT:{
                    SELECT("count(1)");
                }break;
            }
            FROM("[R_User_Role]");
            if (r_User_Role.getId() != null) {
                WHERE("id = #{r_User_Role.id}");
            }
            if (StrUtil.isNotBlank(r_User_Role.getUserUuid())) {
                WHERE("user_uuid = #{r_User_Role.userUuid}");
            }
            if (StrUtil.isNotBlank(r_User_Role.getRoleUuid())) {
                WHERE("role_uuid = #{r_User_Role.roleUuid}");
            }
        }};
        return selectSql;
    }
}