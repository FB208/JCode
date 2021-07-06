import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
public class UserMapperSqlProvider {
    public String selectQuery(@Param("user") User user, @Param("orderColumn")String orderColumn){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM,user,orderColumn,0,0).toString();
    }
    public String selectQueryCount(@Param("user") User user, @Param("orderColumn")String orderColumn){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COUNT,user,orderColumn,0,0).toString();
    }
    public String selectQueryByPage(@Param("user") User user, @Param("orderColumn")String orderColumn, @Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize){
        return  generatorQuerySql(CommonConstant.SQL_BASIC_TYPE.SELECT_COLUNM_BY_PAGE,user,orderColumn,pageIndex,pageSize).toString();
    }
    private SQL generatorQuerySql(CommonConstant.SQL_BASIC_TYPE sqlBasicType,@Param("User") User user,@Param("orderColumn")String orderColumn,@Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize){
        SQL selectSql = new SQL(){{
            switch (sqlBasicType){
                case SELECT_COLUNM:{
                    SELECT("uuid,user_account,user_name,user_password,remark");
                    ORDER_BY(orderColumn);
                }break;
                case SELECT_COLUNM_BY_PAGE:{
                    SELECT("uuid,user_account,user_name,user_password,remark");
                    ORDER_BY(orderColumn);
                    OFFSET((pageIndex-1)*pageSize);
                    FETCH_FIRST_ROWS_ONLY(pageSize);
                }break;
                case SELECT_COUNT:{
                    SELECT("count(1)");
                }break;
            }
            FROM("[User]");
            if (StrUtil.isNotBlank(user.getUuid())) {
                WHERE("uuid = #{user.uuid}");
            }
            if (StrUtil.isNotBlank(user.getUserAccount())) {
                WHERE("user_account = #{user.userAccount}");
            }
            if (StrUtil.isNotBlank(user.getUserName())) {
                WHERE("user_name = #{user.userName}");
            }
            if (StrUtil.isNotBlank(user.getUserPassword())) {
                WHERE("user_password = #{user.userPassword}");
            }
            if (StrUtil.isNotBlank(user.getRemark())) {
                WHERE("remark = #{user.remark}");
            }
        }};
        return selectSql;
    }
}