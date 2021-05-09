package cn.edu.fzu.daoyun.controller;

import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.entity.CollegeDO;
import cn.edu.fzu.daoyun.entity.MajorDO;
import cn.edu.fzu.daoyun.entity.SchoolDO;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import cn.edu.fzu.daoyun.service.impl.OrganizationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequestMapping("/org")
@Api(tags={"学校,学院,专业组织接口"})
public class OrganizationController {
    @Resource
    private OrganizationServiceImpl organizationService;

    @ApiOperation(value = "getSchoolList", notes = "获取学校分页列表")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class)
    })
    @ResponseBody
    @AnonymousGetMapping(value = "/school-list")
    public Result<Page<SchoolDO>> getSchoolList(
            @RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "size", required = true) Integer size
    ) {
        if (page <= 0 || size <= 0) //页数或条数不能小于0
           throw new BadRequestException("页数或条数不能小于0");
        Page<SchoolDO> pageResult = this.organizationService.getSchoolList(page, size);
        System.out.println(pageResult.toString());
        return Result.success(ResultCodeEnum.SUCCESS,pageResult);
    }

    @ApiOperation(value = "getSchool", notes = "根据学校代码获取学校信息")
    @AnonymousGetMapping(value = "/school")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class)
    })
    public Result<SchoolDO> getSchool(
            @RequestParam(value = "schCode", required = true) Integer schCode
    ) {
        if (schCode <= 0) // 学校代码不能为负数
            throw new BadRequestException("学校代码不能为负数");
        SchoolDO school  = this.organizationService.getSchool(schCode);
        return Result.success(ResultCodeEnum.SUCCESS,school);
    }

    @AnonymousGetMapping(value = "/college-list")
    @ApiOperation(value = "getCollegeList", notes = "根据学校代码,获取学院分页信息")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class)
    })
    public Result<Page<CollegeDO>> getCollegeList(
            @RequestParam(value = "schCode",required = true) Integer schCode,
            @RequestParam(value = "page",required = true) Integer page ,
            @RequestParam(value = "size",required = true) Integer size
    ){
        if (schCode <= 0 || page <=0 || size<=0 ) // 页数或条数或学校代码不能为负数
           throw new BadRequestException("页数或条数或学校代码不能为负数");
        Page<CollegeDO> pageResult = this.organizationService.getCollegeList(schCode,page, size);
        return Result.success(ResultCodeEnum.SUCCESS,pageResult);
    }


    @AnonymousGetMapping(value = "/college")
    @ApiOperation(value = "getCollege", notes = "根据学校代码和学院代码,获取学院详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class)
    })
    public Result<CollegeDO> getCollege(
            @RequestParam(value = "schCode",required = true) Integer schCode,
            @RequestParam(value = "colCode",required = true) Integer colCode
            ){
        if (schCode <= 0 || colCode <=0 ) // 学校或学院代码不能为负数
            throw new BadRequestException("学校或学院代码不能为负数");
        CollegeDO college = this.organizationService.getCollege(schCode,colCode);
        return Result.success(ResultCodeEnum.SUCCESS,college);
    }





    @AnonymousGetMapping(value = "/major-list")
    @ApiOperation(value = "getMajorList", notes = "根据学校代码和学院代码,获取专业列表信息")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class)
    })
    public Result<Page<MajorDO>> getMajorList(
            @RequestParam(value = "colCode",required = true) Integer colCode,
            @RequestParam(value = "page",required = true) Integer page,
            @RequestParam(value = "size",required = true) Integer size
    ){
        if (colCode<=0 || page <=0 || size<=0 ) // 页数或条数或学校代码不能为负数
            throw new BadRequestException("页数或条数或学校代码不能为负数");
        Page<MajorDO> pageResult = this.organizationService.getMajorList(colCode,page, size);
        return Result.success(ResultCodeEnum.SUCCESS,pageResult);
    }


    @AnonymousGetMapping(value = "/major")
    @ApiOperation(value = "getMajor", notes = "根据学院代码和专业代码,获取专业信息")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class)
    })
    public Result<MajorDO> getMajor(
            @RequestParam(value = "colCode",required = true) Integer colCode,
            @RequestParam(value = "majCode",required = true) Integer majCode
    ){
        if (colCode<=0 || majCode<=0 ) //学院代码或者专业代码不能为负数
            throw new BadRequestException("学院代码或者专业代码不能为负数");
        MajorDO major = this.organizationService.getMajor(colCode,majCode);
        return Result.success(ResultCodeEnum.SUCCESS,major);
    }






}



