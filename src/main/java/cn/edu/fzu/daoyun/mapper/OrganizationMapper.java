package cn.edu.fzu.daoyun.mapper;


import cn.edu.fzu.daoyun.entity.CollegeDO;
import cn.edu.fzu.daoyun.entity.MajorDO;
import cn.edu.fzu.daoyun.entity.SchoolDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrganizationMapper {
    //获取学校分页列表
    @Select("select * from organization where type=1 limit #{from},#{to};")
    @Results(id = "schoolMap",
    value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "schName"),
            @Result(column = "code", property = "schCode"),
            @Result(column = "describe", property = "schInfo")
    })
    public List<SchoolDO> getSchoolList(Integer from, Integer to);


    //获取所有学校个数
    @Select("select count(*) from organization where type=1;")
    public Integer getSchoolTotal();

    //根据学校代码获取学校信息
    @Select("select * from organization where code=#{schCode};")
    @ResultMap(value = {"schoolMap"})
    public SchoolDO getSchool(Integer schCode);


    //根据学校代码,获取学校相关专业
    @Select("select * from organization where type=2 and parentId=#{schCode} limit #{from},#{to};")
    @Results(id = "collegeMap",value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "colName"),
            @Result(column = "code", property = "colCode"),
            @Result(column = "describe", property = "colInfo")
    })
    public List<CollegeDO> getCollegeList(Integer schCode , Integer from, Integer to);

    //获取学校所开设的学院个数
    @Select("select count(*) from organization where type=2 and parentId=#{schCode};")
    public Integer getCollegeTotal(Integer schCode);

    //根据学校代码和学院代码,获取学院详细信息
    @Select("select * from organization where type=2 and parentId=#{schCode} and code=#{colCode};")
    @ResultMap(value = {"collegeMap"})
    public CollegeDO getCollege(Integer schCode,Integer colCode);



    //根据学校代码和学院代码,获取专业列表信息
    //根据学校代码,获取学校相关专业
    @Select("select * from organization where type=3 and parentId=#{colCode} limit #{from},#{to};")
    @Results(id = "majorMap",value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "majName"),
            @Result(column = "code", property = "majCode"),
            @Result(column = "describe", property = "majInfo")
    })
    public List<MajorDO> getMajorList(Integer colCode, Integer from, Integer to);


    //获取专业个数
    @Select("select count(*) from organization where type=3 and parentId=#{colCode} ;")
    public Integer getMajorTotal(Integer colCode);


    @Select("select * from organization where type=3 and parentId=#{colCode} and code=#{majCode};")
    @ResultMap(value = {"majorMap"})
    public MajorDO getMajor(Integer colCode,Integer majCode);
}
