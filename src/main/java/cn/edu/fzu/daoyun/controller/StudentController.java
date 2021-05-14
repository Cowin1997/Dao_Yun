package cn.edu.fzu.daoyun.controller;

import cn.edu.fzu.daoyun.annotation.AnonymousDeleteMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPutMapping;
import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.entity.StudentDO;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import cn.edu.fzu.daoyun.mapper.StudentMapper;
import cn.edu.fzu.daoyun.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags={"学生信息接口"})
@RequestMapping(value = {"/api/student"})
public class StudentController {
    @Resource
    private StudentService studentService;


    @AnonymousGetMapping(value = "/list")
    @ApiOperation(value = "getStudent",notes = "获取学生列表")
    public Result<Page<StudentDO>> getStudent(@RequestParam(value = "sch") Integer sch,
                                              @RequestParam(value = "col") Integer col,
                                              @RequestParam(value = "maj") Integer maj,
                                              @RequestParam(value = "page") Integer page,
                                              @RequestParam(value = "size") Integer size){

          if(sch>0 && col>0 && maj>0 && page >0 && size >0){
              Page<StudentDO> pageData = this.studentService.getStudentPageByOrg(sch, col, maj, page, size);
              return Result.success(ResultCodeEnum.SUCCESS,pageData);
          }
          throw new BadRequestException("请求参数错误");
    }



    @DeleteMapping(value = "/{sid}")
    @ApiOperation(value = "通过学号,删除学生",notes = "通过学号,删除学生")
    public Result<Boolean> delStudentBySid(@PathVariable(value = "sid",required = true) Integer sid){
        Boolean isSuccess = this.studentService.deleteStudentBySid(sid);
        if(isSuccess==true){
            return Result.success(ResultCodeEnum.DEL_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.DEL_FAILURE);
    }



    @PostMapping(value = "")
    @ApiOperation(value = "addStudent",notes = "添加学生")
    public Result<Boolean> addStudent(@RequestBody StudentDO student){
        Boolean isSuccess = this.studentService.addStudent(student);
        if(isSuccess==true){
            return Result.success(ResultCodeEnum.ADD_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.ADD_FAILURE);
    }




    @AnonymousGetMapping
    @ApiOperation(value = "getStudent",notes = "通过学生学号查找学生")
    public Result<StudentDO> getStudent(@RequestParam("sid") Integer sid){
         StudentDO student = this.studentService.getStudentBySid(sid);
         if(student==null) return Result.failure(ResultCodeEnum.STUDENT_UNEXIST);
         return Result.success(ResultCodeEnum.SUCCESS,student);
    }

    @AnonymousPutMapping(value = "")
    @ApiOperation(value = "updateStudent",notes = "更新学生信息")
    public Result updateStudent(@RequestBody StudentDO student){ ;
        Boolean isSuccess = this.studentService.updateStudentBySid(student);
        if(isSuccess==true) {
            return Result.success(ResultCodeEnum.UPD_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.UPD_FAILURE);

    }










}
