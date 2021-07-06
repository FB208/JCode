import org.apache.ibatis.annotations.*; 

 @Mapper 
 public interface RoleMapper  { 
    @Insert("insert into [Role] (uuid,role_name) values(#{uuid},#{roleName})")
    int insert(Role role);

    @Update("update [Role] set role_name=#{roleName} where uuid=#{uuid}")
    int update(Role role);

    @Delete("delete from [Role] where uuid = #{uuid} ")
    int delete(@Param("uuid")String uuid);

    @Select("select uuid,role_name from [Role]")
    List<Role> selectAll();

    @Select("select uuid,role_name from [Role] where uuid = #{uuid}")
    Role selectOne(@Param("uuid")String uuid);

    @SelectProvider(type = RoleMapperSqlProvider.class, method = "selectQuery")
    List<Role> selectQuery(@Param("role") Role role,@Param("orderColumn")String orderColumn);
    @SelectProvider(type = RoleMapperSqlProvider.class, method = "selectQueryCount")
    int selectQueryCount(@Param("role") Role role,@Param("orderColumn")String orderColumn);
    /**
    * pageIndex：页码，从1开始；pageSize：每页X条
    */
    @SelectProvider(type = RoleMapperSqlProvider.class, method = "selectQueryByPage")
    List<Role> selectQueryByPage(@Param("role") Role role,@Param("orderColumn")String orderColumn,@Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize);
}