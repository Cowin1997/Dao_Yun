package cn.edu.fzu.daoyun.service;

import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.entity.StudentDO;
import com.squareup.okhttp.internal.Internal;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentService {

    /**
     *  根据学号获取学生信息
     * @param sid
     * @return
     */
    public StudentDO getStudentBySid(Integer sid);


    /**
     *  根据学号删除学生(*解绑了账号绑定的学生)
     */
    public Boolean deleteStudentBySid(Integer sid);


    /**
     *  更新学生信息
     */
    public Boolean updateStudentBySid(StudentDO student);


    /**
     *  添加学生(* 必须先添加账号)
     */
    public Boolean addStudent(StudentDO student);


    /**
     *  根据组织信息查询所有学生
     */

    public Page<StudentDO> getStudentPageByOrg(Integer sch, Integer col, Integer maj, Integer from, Integer to);


}
