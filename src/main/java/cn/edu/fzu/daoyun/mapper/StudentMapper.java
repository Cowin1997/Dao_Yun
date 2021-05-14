package cn.edu.fzu.daoyun.mapper;


import cn.edu.fzu.daoyun.entity.StudentDO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface StudentMapper {


    @Select("select * from student where sid=#{sid};")
    public StudentDO selectBySid(Integer sid);

    @Select("select * from student where user_id=#{uid};")
    public StudentDO selectByUserId(Integer uid);

    @Insert("insert into student(user_id,sid,name,gender,phone,school_code,college_code,major_code,gmt_create,gmt_modified) " +
            "values(#{user_id},#{sid},#{name},#{gender},#{phone},#{school_code},#{college_code},#{major_code},#{gmt_create},#{gmt_modified});")
    public Boolean insertStudent(StudentDO student);


    /**
     *  根据用户 id 获取学生信息
     * @param userId
     * @return
     */
    @Select("select * from student where user_id = #{userId};")
    public StudentDO getStudentByUserId(Integer userId);

//    @Select("select count(*) from selectcourse where cs_id=#{cid};")
//    public Integer getTotalByCid(String cid);

//    @Select("select st_id from selectcourse where cs_id=#{cid} limit #{from},#{to};")
//    public List<String> getStudentSidByCid(String cid,Integer from,Integer to);



//    @Select("select count(*) from student where st_school=#{sch};")
//    public Integer getTotalBySch(Integer sch);
//    @Select("Select * from student where st_school=#{sch} limit #{from},#{to};")
//    public List<Student> getStudentBySch(Integer sch,Integer from,Integer to);
//
    @Select("select count(*) from student where school_code=#{sch} and college_code=#{col};")
    public Integer getTotalBySch_Col(Integer sch,Integer col);
    @Select("Select * from student where school_code=#{sch} and college_code=#{col} limit #{from},#{to};")
    public List<StudentDO> getStudentBySch_Col(Integer sch, Integer col, Integer from, Integer to);

    @Select("select count(*) from student where school_code=#{sch} and college_code=#{col} and major_code=#{maj};")
    public Integer getTotalBySch_Col_Maj(Integer sch, Integer col,Integer maj);
    @Select("Select * from student where school_code=#{sch} and college_code=#{col} and major_code=#{maj} limit #{from},#{to};")
    public List<StudentDO> getStudentBySch_Col_Maj(Integer sch, Integer col,Integer maj,Integer from,Integer to);



    @Delete("delete  from student where sid=#{sid};")
    public Boolean delStudentBySid(Integer sid);


    @Update("update student set name=#{name}, gender=#{gender},phone=#{phone},school_code=#{school_code},college_code=#{college_code},major_code=#{major_code},gmt_modified=#{gmt_modified} where sid=#{sid};")
    public Boolean updateStudent(StudentDO student);
}
