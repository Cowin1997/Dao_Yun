package cn.edu.fzu.daoyun.mapper;


import cn.edu.fzu.daoyun.entity.StudentDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {


    @Select("select * from student where sid=#{sid};")
    public StudentDO selectBySid(String sid);


    @Insert("insert into student(user_id,sid,name,gender,phone,school_code,college_code,major_code,gmt_create,gmt_modified) " +
            "values(#{user_id},#{sid},#{name},#{gender},#{phone},#{school_code},#{college_code},#{major_code},#{gmt_create},#{gmt_modified});")
    public Boolean insertStudent(StudentDO student);



//    @Select("select count(*) from selectcourse where cs_id=#{cid};")
//    public Integer getTotalByCid(String cid);

//    @Select("select st_id from selectcourse where cs_id=#{cid} limit #{from},#{to};")
//    public List<String> getStudentSidByCid(String cid,Integer from,Integer to);



//    @Select("select count(*) from student where st_school=#{sch};")
//    public Integer getTotalBySch(Integer sch);
//    @Select("Select * from student where st_school=#{sch} limit #{from},#{to};")
//    public List<Student> getStudentBySch(Integer sch,Integer from,Integer to);
//
//    @Select("select count(*) from student where st_school=#{sch} and st_college=#{col};")
//    public Integer getTotalBySch_Col(Integer sch,Integer col);
//    @Select("Select * from student where st_school=#{sch} and st_college=#{col} limit #{from},#{to};")
//    public List<Student> getStudentBySch_Col(Integer sch, Integer col,Integer from,Integer to);
//
//    @Select("select count(*) from student where st_school=#{sch} and st_college=#{col} and st_major=#{maj};")
//    public Integer getTotalBySch_Col_Maj(Integer sch, Integer col,Integer maj);
//    @Select("Select * from student where st_school=#{sch} and st_college=#{col} and st_major=#{maj} limit #{from},#{to};")
//    public List<Student> getStudentBySch_Col_Maj(Integer sch, Integer col,Integer maj,Integer from,Integer to);
//
//
//
//    @Delete("delete  from student where st_id=#{sid};")
//    public Boolean delStudentBySid(String sid);
//
//
//    @Update("update student set st_name=#{st_name}, st_sex=#{st_sex},st_phone=#{st_phone},st_email=#{st_email},st_school=#{st_school},st_college=#{st_college},st_major=#{st_major},st_grade=#{st_grade} where st_id=#{st_id};")
//    public Boolean updateStudent(Student student);
}
