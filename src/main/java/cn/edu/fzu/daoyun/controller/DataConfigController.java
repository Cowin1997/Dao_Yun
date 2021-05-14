package cn.edu.fzu.daoyun.controller;


import cn.edu.fzu.daoyun.annotation.AnonymousDeleteMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPostMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPutMapping;
import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.dto.SysDictDTO;
import cn.edu.fzu.daoyun.entity.SysDictDO;
import cn.edu.fzu.daoyun.entity.SysParamDO;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import cn.edu.fzu.daoyun.service.SysParamService;
import cn.edu.fzu.daoyun.service.impl.SysDictServiceImpl;
import cn.edu.fzu.daoyun.service.impl.SysParamServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping(value = "/sys")
@Api(tags={"参数配置接口"})
public class DataConfigController {
    @Resource
    private SysDictServiceImpl sysDictService;
    @Resource
    private SysParamServiceImpl sysParamService;

    @AnonymousGetMapping(value = "/params")
    @ResponseBody
    @ApiOperation(value = "getSysParamList",notes = "获取系统参数分页")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "bad request", response = Result.class)
    })
    public Result<Page<SysParamDO>> getSysParamList(
            @RequestParam(value = "page",required = true) Integer page,
            @RequestParam(value = "size",required = true) Integer size
    ){
        if (page <= 0 || size <= 0) //页数或条数不能小于0
            throw new BadRequestException("页数或条数不能小于0");
        Page<SysParamDO> pageResult = this.sysParamService.getSysParamList(page, size);
        return Result.success(ResultCodeEnum.SUCCESS, pageResult);
    }


    @AnonymousDeleteMapping(value = "/param/{id}")
    @ResponseBody
    @ApiOperation(value = "delSysParamById",notes = "根据参数id删除参数配置")
    @Transactional
    public Result<Boolean> delSysParamById(
            @PathVariable(value = "id",required = true) Integer id){
        Boolean aBoolean = this.sysParamService.delSysParam(id);
        if(aBoolean)
            return Result.success(ResultCodeEnum.SUCCESS,true);
        return Result.failure(ResultCodeEnum.FAILURE);
    }




    @AnonymousPostMapping(value = "/param")
    @ResponseBody
    @ApiOperation(value = "addSysParam",notes = "添加系统参数")
    public Result<Boolean> addSysParam(@RequestBody SysParamDO sysParam){
        Boolean aBoolean = this.sysParamService.addSysParam(sysParam);
        if(aBoolean)
            return Result.success(ResultCodeEnum.SUCCESS,true);
        return Result.failure(ResultCodeEnum.FAILURE);
    }




    @AnonymousPutMapping(value = "/param")
    @ResponseBody
    @ApiOperation(value = "updateSysParam",notes = "更新系统参数")
    public Result updateSysParam(@RequestBody SysParamDO param){
        Boolean aBoolean = this.sysParamService.updateSysParam(param);
        if(aBoolean)
            return Result.success(ResultCodeEnum.SUCCESS,true);
        return Result.failure(ResultCodeEnum.FAILURE);
    }


    @AnonymousGetMapping(value = "/dicts")
    @ResponseBody
    @ApiOperation(value = "getDictParamList",notes = "获取所有字典")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "bad request", response = Result.class)
    })
    public Result<Page<SysDictDTO>> getDictParamList(
            @RequestParam(value = "page",required = true) Integer page,
            @RequestParam(value = "size",required = true) Integer size
    ){
        if (page <= 0 || size <= 0) //页数或条数不能小于0
           throw new BadRequestException("页数或条数不能小于0");
        Page<SysDictDTO> pageResult = this.sysDictService.getDictPage(page,size);
        return Result.success(ResultCodeEnum.SUCCESS,pageResult);
    }


    @AnonymousDeleteMapping(value = "/dict/{id}")
    @ResponseBody
    @ApiOperation(value = "deleteDictById",notes = "根据ID删除字典")
    public Result<Boolean> deleteDictById(@PathVariable(value = "id") Integer id){
         Boolean aBoolean = this.sysDictService.deleteParentDict(id);
         return Result.success(ResultCodeEnum.SUCCESS,aBoolean);
    }


    @AnonymousPostMapping(value = "/dict")
    @ResponseBody
    @ApiOperation(value = "addDict",notes = "添加字典")
    public Result<Integer> addDict(@RequestBody SysDictDO sysDict){
        sysDict.setGmt_create(new Date());
        Integer addResult = this.sysDictService.addParentDict(sysDict);
        return Result.success(ResultCodeEnum.SUCCESS,addResult);
    }


    @AnonymousPostMapping(value = "/sub-dict")
    @ResponseBody
    @ApiOperation(value = "addDict",notes = "添加字典")
    public Result<Integer> addSubDict(@RequestBody SysDictDO sysDict){
        sysDict.setGmt_create(new Date());
        Integer addResult = this.sysDictService.addSubDict(sysDict);
        return Result.success(ResultCodeEnum.SUCCESS,addResult);
    }


    @AnonymousPutMapping(value = "/dict")
    @ResponseBody
    @ApiOperation(value = "updateDict",notes = "更新父级字典")
    public Result<Boolean> updateParentDict(@RequestBody SysDictDO sysDict) {
        Boolean updateResult = this.sysDictService.updateParentDict(sysDict);
        return Result.success(ResultCodeEnum.SUCCESS,updateResult);
    }
    @AnonymousPutMapping(value = "/sub-dict")
    @ResponseBody
    @ApiOperation(value = "updateDict",notes = "更新子级字典")
    public Result<Boolean> updateSubDict(@RequestBody SysDictDO sysDict) {
        Boolean updateResult = this.sysDictService.updateSubDict(sysDict);
        return Result.success(ResultCodeEnum.SUCCESS,updateResult);
    }


}

