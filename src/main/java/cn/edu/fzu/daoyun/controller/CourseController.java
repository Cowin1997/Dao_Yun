package cn.edu.fzu.daoyun.controller;

import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPostMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPutMapping;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.dto.CourseDTO;
import cn.edu.fzu.daoyun.entity.CourseDO;
import cn.edu.fzu.daoyun.entity.StudentDO;
import cn.edu.fzu.daoyun.mapper.CourseMapper;
import cn.edu.fzu.daoyun.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags={"班课管理接口"})
@RequestMapping("/api/course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private CourseMapper courseMapper;
    @AnonymousPostMapping(value = "")
    @ApiOperation(value = "addCourse",notes = "添加班课")
    public Result<Boolean> addCourse(@RequestBody CourseDO course){
        Boolean isSuccess = this.courseService.addCourse(course);
        if(isSuccess==true){
            return Result.success(ResultCodeEnum.ADD_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.ADD_FAILURE);
    }


    @AnonymousPutMapping(value = "")
    @ApiOperation(value = "updateCourse",notes = "更新班课信息")
    public Result updateCourse(@RequestBody CourseDO course){ ;
        Boolean isSuccess = this.courseService.updateCourse(course);
        if(isSuccess==true) {
            return Result.success(ResultCodeEnum.UPD_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.UPD_FAILURE);
    }

    @AnonymousGetMapping(value = "")
    @ApiOperation(value = "getCourse",notes = "根据班课Id获取班课信息")
    public Result getCourse(@RequestParam("cid") Integer cid) {
        CourseDO course = this.courseService.getCourseByCid(cid);
        if(course!=null){
            return Result.success(ResultCodeEnum.SUCCESS,course);
        }
        return Result.failure(ResultCodeEnum.COURSE_UNEXIST);
    }
    @AnonymousGetMapping(value = "/s")
    @ApiOperation(value = "getCourseByStudent",notes = "根据学生ID获取该学生的所有课程")
    public Result getCourseByStudent(@RequestParam("sid") Integer sid) {
        List<CourseDTO> courseList = this.courseService.getCourseBySid(sid);
         return Result.success(ResultCodeEnum.SUCCESS,courseList);

    }

    @AnonymousGetMapping(value = "/t")
    @ApiOperation(value = "getCourseByTeacher",notes = "根据教师ID获取该学生的所有课程")
    public Result getCourseByTeacher(@RequestParam("tid") Integer tid) {
        List<CourseDO> coueseList = this.courseService.getCourseByTid(tid);
        return Result.success(ResultCodeEnum.SUCCESS,coueseList);
    }


    @AnonymousGetMapping(value = "/c")
    @ApiOperation(value = "getStudentListByClassid",notes = "根据教师ID获取该学生的所有课程")
    public Result getStudentListByClassid(@RequestParam("cid") Integer cid) {
        List<StudentDO> studentByClassId = this.courseMapper.getStudentByClassId(cid);
        return Result.success(ResultCodeEnum.SUCCESS,studentByClassId);
    }
}












