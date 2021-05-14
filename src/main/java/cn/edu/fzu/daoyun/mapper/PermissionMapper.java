package cn.edu.fzu.daoyun.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {
    @Select("select menu_id from permission where role_id=#{roleId};")
    public List<Integer> getPermission(Integer roleId);
}
