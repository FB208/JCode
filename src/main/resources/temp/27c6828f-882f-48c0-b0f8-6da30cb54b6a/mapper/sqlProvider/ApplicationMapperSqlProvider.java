import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
public class ApplicationMapperSqlProvider {
    public String selectQuery(@Param("application") Application application, @Param("orderColumn")String orderColumn){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM,application,orderColumn,0,0).toString();
    }
    public String selectQueryCount(@Param("application") Application application, @Param("orderColumn")String orderColumn){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COUNT,application,orderColumn,0,0).toString();
    }
    public String selectQueryByPage(@Param("application") Application application, @Param("orderColumn")String orderColumn, @Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM_BY_PAGE,application,orderColumn,pageIndex,pageSize).toString();
    }
    private SQL generatorQuerySql(CommonConstant.SQL_BASIC_TYPE sqlBasicType,@Param("Application") Application application,@Param("orderColumn")String orderColumn,@Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize){
        SQL selectSql = new SQL(){{
            switch (sqlBasicType){
                case SELECT_COLUNM:{
                    SELECT("id,application_name,token,register_date,is_self");
                    ORDER_BY(orderColumn);
                }break;
                case SELECT_COLUNM_BY_PAGE:{
                    SELECT("id,application_name,token,register_date,is_self");
                    ORDER_BY(orderColumn);
                    OFFSET((pageIndex-1)*pageSize);
                    FETCH_FIRST_ROWS_ONLY(pageSize);
                }break;
                case SELECT_COUNT:{
                    SELECT("count(1)");
                }break;
            }
            FROM("[Application]");
            if (application.getId() != null) {
                WHERE("id = #{application.id}");
            }
            if (StrUtil.isNotBlank(application.getApplicationName())) {
                WHERE("application_name = #{application.applicationName}");
            }
            if (StrUtil.isNotBlank(application.getToken())) {
                WHERE("token = #{application.token}");
            }
            if (application.getRegisterDate() != null) {
                WHERE("register_date = #{application.registerDate}");
            }
            if (application.getIsSelf() != null) {
                WHERE("is_self = #{application.isSelf}");
            }
        }};
        return selectSql;
    }
}