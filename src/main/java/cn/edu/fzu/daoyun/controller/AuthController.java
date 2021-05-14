package cn.edu.fzu.daoyun.controller;


import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPostMapping;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.dto.JwtUserDTO;
import cn.edu.fzu.daoyun.dto.StudentDTO;
import cn.edu.fzu.daoyun.dto.TeacherDTO;
import cn.edu.fzu.daoyun.entity.UserDO;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import cn.edu.fzu.daoyun.query.*;
import cn.edu.fzu.daoyun.service.impl.LoginServiceImpl;
import cn.edu.fzu.daoyun.utils.SmsUtils;
import cn.edu.fzu.daoyun.utils.captcha.CaptChaUtils;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import cn.edu.fzu.daoyun.vo.CaptchaVO;
import cn.edu.fzu.daoyun.vo.LoginVO;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;



@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "系统授权登录和注册接口")
@RequestMapping("/api/auth")
public class AuthController {

    private final RedisUtils redisUtils;
    private final LoginServiceImpl loginService;


    @AnonymousPostMapping(value = "/login")
    @ApiOperation("登录接口")
    @ApiResponses( //用于表示一组响应
            //用在@ApiResponses中，一般用于表达一个错误的响应信息
            {
                    @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class),
                    @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "服务器内部错误", response = Result.class)
            }
    )
    public Result<JwtUserDTO> login(@RequestBody @Valid LoginQuery loginQuery){
        JwtUserDTO jwtUserDTO = null;
        // 验证码检验
//        Result codeResult = captChaUtils.checkValid(loginQuery.getUuid(),loginQuery.getCode());
//        if(!codeResult.getSuccess()) return codeResult;
        String token = null;
        switch (loginQuery.getType()){
            case 1:  // 用户名密码登录
                jwtUserDTO = loginService.loginByLocal(loginQuery);
                break;
            case 2: // 短信验证码登录
                //校验短信验证码
//                String code = (String) redisUtils.get(loginQuery.getIdentifier());
//                if(code==null) return Result.failure(ResultCodeEnum.CODE_EXPIRED);
//                if(!StringUtils.equals(code,loginQuery.getCredential())) return Result.failure(ResultCodeEnum.CODE_INVAILD);
//                // 登录成功返回 token,登录异常交给 Springseurity 异常处理
                jwtUserDTO = loginService.loginByPhone(loginQuery);
                break;
            default:
                throw new BadRequestException("不支持该登录类型");
        }


        // 返回响应
        return Result.success(ResultCodeEnum.LOGIN_SUCCESS, jwtUserDTO);
    }




    @AnonymousPostMapping(value = "/register")
    public Result register(@RequestBody @Valid RegisterQuery registerQuery){
        // 验证短信验证码
//        String code = (String) redisUtils.get(registerQuery.getPhone());
//        if(code==null) return Result.failure(ResultCodeEnum.CODE_EXPIRED);
//        if(!StringUtils.equals(code,registerQuery.getSmsCode())) return Result.failure(ResultCodeEnum.CODE_INVAILD);
        // 注册
        switch (registerQuery.getType()){
//            case 1: //获取token查看是否是超级管理员,才可以注册管理员
//
            case 3:
                TeacherDTO teacherDTO = loginService.registTeacher(registerQuery);
                return Result.success(ResultCodeEnum.REGIST_SUCCESS,teacherDTO);
            case 4:
                StudentDTO studentDTO = loginService.registStudent(registerQuery);
                return Result.success(ResultCodeEnum.REGIST_SUCCESS,studentDTO);
            default:
                throw new BadRequestException("不支持该注册类型");
        }
    }

    @AnonymousPostMapping(value = "/fast-register")
    public Result fastRegister(@RequestBody @Valid FastRegisterQuery registerQuery){
        // 验证短信验证码
//        String code = (String) redisUtils.get(registerQuery.getPhone());
//        if(code==null) return Result.failure(ResultCodeEnum.CODE_EXPIRED);
//        if(!StringUtils.equals(code,registerQuery.getSmsCode())) return Result.failure(ResultCodeEnum.CODE_INVAILD);
        // 快速注册,出现问题抛异常统一处理
        UserDO user = loginService.fastRegist(registerQuery);
        return Result.success(ResultCodeEnum.REGIST_SUCCESS,user);
    }


    @ResponseBody
    @AnonymousPostMapping(value = "/pwd-reset")
    @ApiOperation(value = "pwdForget",notes = "短信验证找回密码")
    public Result pwdForget(@RequestBody PwdForgetQuery query){
        // 验证短信验证码
//        String code = (String) redisUtils.get(query.getPhone());
//        if(code==null) return Result.failure(ResultCodeEnum.CODE_EXPIRED);
//        if(!StringUtils.equals(code,query.getSmsCode())) return Result.failure(ResultCodeEnum.CODE_INVAILD);
        //
        boolean isResetSuccess =  loginService.pwdReset(query);
        if(isResetSuccess) return Result.success(ResultCodeEnum.PWDRESET_SUCCESS);
        return Result.failure(ResultCodeEnum.PWDRESET_FAILURE);
    }


}
