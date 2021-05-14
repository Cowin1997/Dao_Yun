package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.entity.TeacherDO;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import cn.edu.fzu.daoyun.mapper.TeacherMapper;
import cn.edu.fzu.daoyun.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;


    @Override
    public TeacherDO getTeacherByTid(Integer tid) {
       return this.teacherMapper.getTeacherByTid(tid);
    }

    @Override
    public Boolean addTeacherByTid(TeacherDO teacher) {
        TeacherDO t = this.teacherMapper.getTeacherByTid(teacher.getTid());
        if(t!=null) throw new BadRequestException("该教师工号已经注册");
        teacher.setGmt_create(new Date());
        return this.teacherMapper.insertTeacher(teacher);
    }

    @Override
    public Boolean delTeacherByTid(Integer tid) {
        return this.delTeacherByTid(tid);
    }

    @Override
    public Boolean updTeacherByTid(TeacherDO teacher) {
        return this.teacherMapper.updateTeacher(teacher);
    }
}
