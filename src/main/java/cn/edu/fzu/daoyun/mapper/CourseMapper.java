package cn.edu.fzu.daoyun.mapper;

import cn.edu.fzu.daoyun.dto.CourseDTO;
import cn.edu.fzu.daoyun.entity.CourseDO;
import cn.edu.fzu.daoyun.entity.StudentDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    // 根据班课号获取
    @Select("select * from course where cid=#{cid};")
    public CourseDO getCourseByCid(Integer cid);

    // 根据教师ID获取所有该教师班课
    @Select("select * from course where teacher_id = #{tid};")
    public List<CourseDO> getCourseListByTid(Integer tid);

    // 添加班课信息
    @Insert("insert into course (cid,teacher_tid,enabled,coursename,detailinfo,school_code,college_code,major_code,gmt_create) " +
            "values (#{cid},#{teacher_tid},#{enabled},#{coursename},#{detailinfo},#{school_code},#{college_code},#{major_code},#{gmt_create});")
    public Boolean addCourse(CourseDO course);

    // 编辑班课信息
    @Insert("update course set coursename=#{coursename},enabled=#{enabled},detailinfo=#{detailinfo} where cid = #{cid};")
    public Boolean updateCourse(CourseDO course);


    // 根据学号获取学生的所有课程
    @Select("select s.*, sc.score,sc.totalcheck,sc.hascheck\n" +
            " from student s,select_course sc where s.sid = sc.student_sid and s.sid = #{sid};")
    public List<CourseDTO> getCourseBySid(Integer sid);


    // 根据学号获取教师的所有课程
    @Select("select * from course where teacher_tid=#{tid};")
    public List<CourseDO> getCourseByTid(Integer tid);

    @Select("select s.* from student s,select_course sc where s.sid=sc.student_sid and sc.course_cid = #{cid}; ")
    public List<StudentDO> getStudentByClassId(Integer cid);
}
