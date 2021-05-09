package cn.edu.fzu.daoyun.mapper;


import cn.edu.fzu.daoyun.entity.TeacherDO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeacherMapper {

    @Select("select * from teacher where tid=#{tid};")
    public TeacherDO selectByTid(Integer tid);

    @Insert("insert into teacher(user_id,tid,name,gender,phone,school_code,college_code,major_code,gmt_create,gmt_modified)" +
            "values(#{user_id},#{tid},#{name},#{gender},#{phone},#{school_code},#{college_code},#{major_code},#{gmt_create},#{gmt_modified});")
    public Boolean insertTeacher(TeacherDO teacher);


//    @Delete("delete from teacher where te_id=#{tid};")
//    public Boolean deleteTeacherByTid(Integer tid);
//
//
//    @Update("update teacher set te_name=#{te_name},te_phone=#{te_phone},te_email=#{te_email},te_sex=#{te_sex},te_info=#{te_info},te_rank=#{te_rank},te_edu=#{te_edu} where te_id=#{te_id};")
//    public Boolean updateTeacher(Teacher teacher);

//
//    @Select("select * from teacher where te_schoolcode=#{school} and te_collegeid=#{college} and te_majorid=#{major};")
//    public List<Teacher> getTeacherList(Integer school,Integer college,Integer major);
//

}
