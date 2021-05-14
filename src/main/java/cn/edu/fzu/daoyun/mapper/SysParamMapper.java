package cn.edu.fzu.daoyun.mapper;

import cn.edu.fzu.daoyun.entity.SysParamDO;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SysParamMapper {

    @Select("select * from sys_param limit #{from},#{to};")
    public List<SysParamDO> getSysParamList(Integer from, Integer to);



    @Select("select count(*) from sys_param;")
    public Integer getSysParamTotal();

    @Select("select * from sys_param where arg_key=#{key};")
    public SysParamDO getSysParamByKey(String key);


    @Select("select * from sys_param where id=#{id};")
    public SysParamDO getSysParamById(Integer id);

    @Delete("delete from sys_param where id=#{id};")
    public Boolean delSysParam(Integer id);

    @Insert("insert into sys_param (arg_key,arg_value,arg_desc,creator,gmt_create) values (#{arg_key},#{arg_value},#{arg_desc},#{creator},#{gmt_create});")
    public Boolean addSysParam(SysParamDO param);

    @Update("update sys_param set arg_key=#{arg_key},arg_value=#{arg_value},arg_desc=#{arg_desc},reviser=#{reviser},gmt_modified=#{gmt_modified} where id=#{id};")
    public Boolean updateSysParam(SysParamDO param);

}
