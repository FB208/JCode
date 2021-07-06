import org.apache.ibatis.annotations.*; 

 @Mapper 
 public interface ApplicationMapper  { 
    @Insert("insert into [Application] (application_name,token,register_date,is_self) values(#{applicationName},#{token},#{registerDate},#{isSelf})")
    int insert(Application application);

    @Update("update [Application] set application_name=#{applicationName},token=#{token},register_date=#{registerDate},is_self=#{isSelf} where id=#{id}")
    int update(Application application);

    @Delete("delete from [Application] where id = #{id} ")
    int delete(@Param("id")Integer id);

    @Select("select id,application_name,token,register_date,is_self from [Application]")
    List<Application> selectAll();

    @Select("select id,application_name,token,register_date,is_self from [Application] where id = #{id}")
    Application selectOne(@Param("id")Integer id);

    @SelectProvider(type = ApplicationMapperSqlProvider.class, method = "selectQuery")
    List<Application> selectQuery(@Param("application") Application application,@Param("orderColumn")String orderColumn);
    @SelectProvider(type = ApplicationMapperSqlProvider.class, method = "selectQueryCount")
    int selectQueryCount(@Param("application") Application application,@Param("orderColumn")String orderColumn);
    /**
    * pageIndex：页码，从1开始；pageSize：每页X条
    */
    @SelectProvider(type = ApplicationMapperSqlProvider.class, method = "selectQueryByPage")
    List<Application> selectQueryByPage(@Param("application") Application application,@Param("orderColumn")String orderColumn,@Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize);
}