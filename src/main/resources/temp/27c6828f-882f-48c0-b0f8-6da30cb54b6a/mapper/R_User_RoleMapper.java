import org.apache.ibatis.annotations.*; 

 @Mapper 
 public interface R_User_RoleMapper  { 
    @Insert("insert into [R_User_Role] (user_uuid,role_uuid) values(#{userUuid},#{roleUuid})")
    int insert(R_User_Role r_User_Role);

    @Update("update [R_User_Role] set user_uuid=#{userUuid},role_uuid=#{roleUuid} where id=#{id}")
    int update(R_User_Role r_User_Role);

    @Delete("delete from [R_User_Role] where id = #{id} ")
    int delete(@Param("id")Long id);

    @Select("select id,user_uuid,role_uuid from [R_User_Role]")
    List<R_User_Role> selectAll();

    @Select("select id,user_uuid,role_uuid from [R_User_Role] where id = #{id}")
    R_User_Role selectOne(@Param("id")Long id);

    @SelectProvider(type = R_User_RoleMapperSqlProvider.class, method = "selectQuery")
    List<R_User_Role> selectQuery(@Param("r_User_Role") R_User_Role r_User_Role,@Param("orderColumn")String orderColumn);
    @SelectProvider(type = R_User_RoleMapperSqlProvider.class, method = "selectQueryCount")
    int selectQueryCount(@Param("r_User_Role") R_User_Role r_User_Role,@Param("orderColumn")String orderColumn);
    /**
    * pageIndex：页码，从1开始；pageSize：每页X条
    */
    @SelectProvider(type = R_User_RoleMapperSqlProvider.class, method = "selectQueryByPage")
    List<R_User_Role> selectQueryByPage(@Param("r_User_Role") R_User_Role r_User_Role,@Param("orderColumn")String orderColumn,@Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize);
}