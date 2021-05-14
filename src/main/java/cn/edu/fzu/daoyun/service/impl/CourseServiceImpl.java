package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.dto.CourseDTO;
import cn.edu.fzu.daoyun.entity.CourseDO;
import cn.edu.fzu.daoyun.mapper.CourseMapper;
import cn.edu.fzu.daoyun.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Override
    public CourseDO getCourseByCid(Integer cid) {
        return this.courseMapper.getCourseByCid(cid);
    }

    @Override
    public List<CourseDO> getCourseListByTid(Integer tid) {
        return this.courseMapper.getCourseListByTid(tid);
    }

    @Override
    public Boolean addCourse(CourseDO course) {
        course.setGmt_create(new Date());
        return this.courseMapper.addCourse(course);
    }

    @Override
    public Boolean updateCourse(CourseDO course) {
        course.setGmt_modified(new Date());
        return this.courseMapper.updateCourse(course);
    }

    @Override
    public List<CourseDTO> getCourseBySid(Integer sid) {
        return this.courseMapper.getCourseBySid(sid);
    }

    @Override
    public List<CourseDO> getCourseByTid(Integer tid) {
        return this.courseMapper.getCourseByTid(tid);
    }


}
