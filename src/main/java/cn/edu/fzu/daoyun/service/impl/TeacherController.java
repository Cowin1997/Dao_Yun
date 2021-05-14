package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPostMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPutMapping;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.entity.TeacherDO;
import cn.edu.fzu.daoyun.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags={"教师信息接口"})
@RequestMapping(value = {"/api/teacher"})
public class TeacherController {
    @Resource
    private TeacherService teacherService;


    @AnonymousGetMapping
    @ApiOperation(value = "getTeacher",notes = "通过教师工号查找教师")
    public Result<TeacherDO> getStudent(@RequestParam("tid") Integer tid){
        TeacherDO teacher = this.teacherService.getTeacherByTid(tid);
        if(teacher==null) return Result.failure(ResultCodeEnum.TEACHER_UNEXIST);
        return Result.success(ResultCodeEnum.SUCCESS,teacher);
    }


    @DeleteMapping(value = "/{tid}")
    @ApiOperation(value = "通过工号,删除教师",notes = "通过工号,删除教师")
    public Result<Boolean> delStudentBySid(@PathVariable(value = "tid",required = true) Integer tid){
        Boolean isSuccess = this.teacherService.delTeacherByTid(tid);
        if(isSuccess==true){
            return Result.success(ResultCodeEnum.DEL_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.DEL_FAILURE);
    }
    @AnonymousPostMapping(value = "")
    @ApiOperation(value = "addTeacher",notes = "添加教师")
    public Result<Boolean> addTeacher(@RequestBody TeacherDO teacher){
        Boolean isSuccess = this.teacherService.addTeacherByTid(teacher);
        if(isSuccess==true){
            return Result.success(ResultCodeEnum.ADD_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.ADD_FAILURE);
    }

    @AnonymousPutMapping(value = "")
    @ApiOperation(value = "updateTeacher",notes = "更新教师信息")
    public Result updateTeacher(@RequestBody TeacherDO teacher){ ;
        Boolean isSuccess = this.teacherService.updTeacherByTid(teacher);
        if(isSuccess==true) {
            return Result.success(ResultCodeEnum.UPD_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.UPD_FAILURE);

    }


}
