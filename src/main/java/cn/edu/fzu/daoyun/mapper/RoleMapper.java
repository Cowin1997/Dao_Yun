package cn.edu.fzu.daoyun.mapper;

import cn.edu.fzu.daoyun.entity.RoleDO;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    // 获取所有角色
    @Select("select * from role order by level asc;")
    public List<RoleDO> getAll();

    // 添加角色
    @Insert("insert into role(name,level,description,gmt_create,gmt_modified,creator,modifier) " +
            "values (#{name},#{level},#{description},#{gmt_create},#{gmt_modified},#{creator},#{modifier});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Boolean addRole(RoleDO role);

    // 根据角色id,删除角色
    @Delete("delete from role where id=#{roleId};")
    public Boolean delRole(Integer roleId);


    // 更新角色信息
    @Update("update role set name=#{name},level=#{level},description=#{description},gmt_modified=#{gmt_modified},modifier=#{modifier} where id=#{id};")
    public Boolean updRole(RoleDO role);

    @Select("select * from role where id=#{id};")
    public RoleDO getRoleById(Integer id);


}
