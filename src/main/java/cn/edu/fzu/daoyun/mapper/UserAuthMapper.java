package cn.edu.fzu.daoyun.mapper;

import cn.edu.fzu.daoyun.entity.UserAuthDO;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

@Mapper
public interface UserAuthMapper {
    @Select("select * from user_auth where identifier=#{identifier};")
    public UserAuthDO selectByIdentifier(String identifier);
}
