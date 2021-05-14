package cn.edu.fzu.daoyun.service;

import cn.edu.fzu.daoyun.entity.TeacherDO;
import io.swagger.models.auth.In;

public interface TeacherService {
    /*
        根据 tid 获取教师信息
     */
    public TeacherDO getTeacherByTid(Integer tid);
    /*
    根据 tid 获取教师信息
    */
    public Boolean addTeacherByTid(TeacherDO teacher);

    /*
    根据 tid 删除教师信息
    */
    public Boolean delTeacherByTid(Integer tid);

    /*
    根据 tid 删除教师信息
    */
    public Boolean updTeacherByTid(TeacherDO teacher);

}
