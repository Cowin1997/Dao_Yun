package cn.edu.fzu.daoyun.controller;

import cn.edu.fzu.daoyun.annotation.AnonymousDeleteMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPostMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPutMapping;
import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.dto.RoleDTO;
import cn.edu.fzu.daoyun.entity.RoleDO;
import cn.edu.fzu.daoyun.service.RoleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags={"角色信息接口"})
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @AnonymousGetMapping(value = "/all")
    public Result<List<RoleDTO>> getAll(){
        List<RoleDTO> all = this.roleService.getAll();
        return Result.success(ResultCodeEnum.SUCCESS,all);
    }

    @AnonymousPostMapping
    public Result addRole(@RequestBody RoleDO role){
        Boolean isSuccess = this.roleService.addRole(role);
        if(isSuccess){   //成功
            HashMap map = new HashMap();
            map.put("id",role.getId());
            return Result.success(ResultCodeEnum.ADD_SUCCESS, map);
        }
         return Result.failure(ResultCodeEnum.ADD_FAILURE);
    }

   @DeleteMapping(value = "/{id}")
   public Result delRole(@PathVariable("id") Integer id ){
       Boolean isSuccess = this.roleService.delRole(id);
       if(isSuccess){   //成功
           return Result.success(ResultCodeEnum.DEL_SUCCESS);
       }
       return Result.failure(ResultCodeEnum.DEL_FAILURE);
   }




    @AnonymousPutMapping
    public Result updRole(@RequestBody RoleDO role){
        Boolean isSuccess = this.roleService.updRole(role);
        if(isSuccess){   //成功
            return Result.success(ResultCodeEnum.UPD_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.UPD_FAILURE);
    }



}
