package cn.edu.fzu.daoyun.mapper;

import cn.edu.fzu.daoyun.entity.UserAuthDO;
import cn.edu.fzu.daoyun.entity.UserDO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id};")
    public UserDO selectUserById(Integer id);

    @Select("select * from user_auth where identifier=#{identifier};")
    public UserAuthDO selectUserAuthByIdentifier(String identifier);

    @Insert("insert into user(nickname,avatar,role_id,enabled,gmt_create,gmt_modified) values (" +
            "#{nickname},#{avatar},#{role_id},#{enabled},#{gmt_create},#{gmt_modified});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Boolean insertUser(UserDO user);

    @Insert("insert into user_auth(user_id,identity_type,identifier,credential,gmt_create,gmt_modified) values (" +
            "#{user_id},#{identity_type},#{identifier},#{credential},#{gmt_create},#{gmt_modified});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Boolean insertUserAuth(UserAuthDO userAuth);

    @Update("update user_auth set credential=#{newPass} where identifier=#{phone};")
    public Boolean updatePwd(String phone, String newPass);
}
