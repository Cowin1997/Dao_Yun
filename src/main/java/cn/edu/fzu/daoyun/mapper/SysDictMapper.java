package cn.edu.fzu.daoyun.mapper;


import cn.edu.fzu.daoyun.entity.SysDictDO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysDictMapper {

    @Select("select * from sys_dict where parent_id is null limit #{from},#{to};")
    public List<SysDictDO> getDictParentList(Integer from , Integer to);

    @Select("select * from sys_dict where parent_id = #{parent_id};")
    public List<SysDictDO> getSubDictList(Integer parent_id);
    @Select("select count(*) from sys_dict where parent_id is null;")
    public Integer getSysDictTotal();

    @Insert("insert into sys_dict(code,type,creator,gmt_create) values (" +
            "#{code},#{type},#{creator},#{gmt_create});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Integer addParentDict(SysDictDO dict);

    @Insert("insert into sys_dict(code,detail,value,creator,gmt_create,parent_id) values (" +
            "#{code},#{detail},#{value},#{creator},#{gmt_create},#{parent_id});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public Integer addSubDict(SysDictDO dict);


    @Delete("delete from sys_dict where id = #{id};")
    public Boolean deleteParentDict(Integer id);


    @Update("update sys_dict set code=#{code},type=#{type},reviser=#{reviser},gmt_modified=#{gmt_modified} where id=#{id};")
    public Boolean updateParentDict(SysDictDO dict);

    @Update("update sys_dict set value=#{value},detail=#{detail},reviser=#{reviser},gmt_modified=#{gmt_modified} where id=#{id};")
    public Boolean updateSubDict(SysDictDO dict);
}
