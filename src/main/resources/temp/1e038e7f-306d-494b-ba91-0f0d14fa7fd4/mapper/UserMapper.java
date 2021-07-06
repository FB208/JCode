import org.apache.ibatis.annotations.*; 

 @Mapper 
 public interface UserMapper  { 
    @Insert("insert into [User] (uuid,user_account,user_name,user_password,remark) values(#{uuid},#{userAccount},#{userName},#{userPassword},#{remark})")
    int insert(User user);

    @Update("update [User] set user_account=#{userAccount},user_name=#{userName},user_password=#{userPassword},remark=#{remark} where uuid=#{uuid}")
    int update(User user);

    @Delete("delete from [User] where uuid = #{uuid} ")
    int delete(@Param("uuid")String uuid);

    @Select("select uuid,user_account,user_name,user_password,remark from [User]")
    List<User> selectAll();

    @Select("select uuid,user_account,user_name,user_password,remark from [User] where uuid = #{uuid}")
    User selectOne(@Param("uuid")String uuid);

    @SelectProvider(type = UserMapperSqlProvider.class, method = "selectQuery")
    List<User> selectQuery(@Param("user") User user,@Param("orderColumn")String orderColumn);
    @SelectProvider(type = UserMapperSqlProvider.class, method = "selectQueryCount")
    int selectQueryCount(@Param("user") User user,@Param("orderColumn")String orderColumn);
    /**
    * pageIndex：页码，从1开始；pageSize：每页X条
    */
    @SelectProvider(type = UserMapperSqlProvider.class, method = "selectQueryByPage")
    List<User> selectQueryByPage(@Param("user") User user,@Param("orderColumn")String orderColumn,@Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize);
}