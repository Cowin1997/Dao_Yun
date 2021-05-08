package cn.edu.fzu.daoyun.mapper;

import cn.edu.fzu.daoyun.entity.UserDO;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id};")
    public UserDO selectById(Integer id);
}
