package cn.edu.fzu.daoyun.mapper;


import cn.edu.fzu.daoyun.entity.TeacherDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Select("select * from teacher where tid=#{tid};")
    public TeacherDO getTeacherByTid(Integer tid);

    @Select("select * from teacher where user_id=#{id};")
    public TeacherDO getTeacherByUserId(Integer uid);

    @Insert("insert into teacher(user_id,tid,name,gender,phone,school_code,college_code,major_code,gmt_create)" +
            "values(#{user_id},#{tid},#{name},#{gender},#{phone},#{school_code},#{college_code},#{major_code},#{gmt_create});")
    public Boolean insertTeacher(TeacherDO teacher);


    @Delete("delete from teacher where tid=#{tid};")
    public Boolean deleteTeacherByTid(Integer tid);


    @Update("update teacher set name=#{name},phone=#{phone},gender=#{gender},school_code=#{school_code},college_code=#{college_code},major_code=#{major_code} where tid=#{tid};")
    public Boolean updateTeacher(TeacherDO teacher);


    @Select("select * from teacher where school_code=#{school} and college_code=#{college} and major_code=#{major};")
    public List<TeacherDO> getTeacherList(Integer school, Integer college, Integer major);


}
