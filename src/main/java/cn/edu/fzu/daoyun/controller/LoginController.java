package cn.edu.fzu.daoyun.controller;


import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPostMapping;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import cn.edu.fzu.daoyun.query.LoginQuery;
import cn.edu.fzu.daoyun.service.impl.LoginServiceImpl;
import cn.edu.fzu.daoyun.utils.SmsUtils;
import cn.edu.fzu.daoyun.utils.captcha.CaptChaUtils;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import cn.edu.fzu.daoyun.vo.CaptchaVO;
import cn.edu.fzu.daoyun.query.SmsCodeQuery;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;



@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "登录控制器(登录,验证码)")
public class LoginController {
    private final CaptChaUtils captChaUtils;
    private final RedisUtils redisUtils;
    private final LoginServiceImpl loginService;
    private final SmsUtils smsUtils;

    @AnonymousPostMapping(value = "/login")
    @ApiOperation("登录接口")
    @ApiResponses( //用于表示一组响应
            //用在@ApiResponses中，一般用于表达一个错误的响应信息
            {
                    @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class),
                    @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "服务器内部错误", response = Result.class)
            }
    )
    public Result<String> login(@RequestBody @Valid LoginQuery loginQuery){
        // 验证码检验
        Result codeResult = captChaUtils.checkValid(loginQuery.getUuid(),loginQuery.getCode());
        if(!codeResult.getSuccess()) return codeResult;
        String token = null;
        switch (loginQuery.getType()){
            case 1:  // 用户名密码登录
                token = loginService.loginByLocal(loginQuery);
                break;
            case 2: // 短信验证码登录
                //校验短信验证码
                String code = (String) redisUtils.get(loginQuery.getIdentifier());
                if(code==null) return Result.failure(ResultCodeEnum.CODE_EXPIRED);
                if(!StringUtils.equals(code,loginQuery.getCredential())) return Result.failure(ResultCodeEnum.CODE_INVAILD);
                // 登录成功返回 token,登录异常交给 Springseurity 异常处理
                token = loginService.loginByPhone(loginQuery);
                break;
            default:
                throw new BadRequestException("不支持该登录类型");
        }


        // 返回响应
        return Result.success(ResultCodeEnum.LOGIN_SUCCESS, token);
    }


    @ApiOperation("获取图形验证码")
    @AnonymousGetMapping(value = "/captcha")
    @ApiResponses( //用于表示一组响应
            //用在@ApiResponses中，一般用于表达一个错误的响应信息
            {
                    @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "服务器内部错误", response = Result.class)
            }
    )
    public Result<CaptchaVO> getCaptcha(){
        // 获取验证码
        Captcha captcha = captChaUtils.getCaptcha();
        String captchaValue = captcha.text(); //验证码正确结果
        // 生成UUID
        String uuid = UUID.randomUUID().toString();
        // 返回视图
        CaptchaVO captchaVO = new CaptchaVO(captcha.toBase64(),uuid);
        // 验证码结果
        log.info(uuid+":"+captchaValue);
        // 存入redis
        redisUtils.set(uuid,captchaValue,captChaUtils.getExpiration());
        return Result.success(ResultCodeEnum.CAPTCHA_SUCCESS,captchaVO);
    }

    @ApiOperation("获取短信验证码")
    @AnonymousPostMapping(value = "/smsCode")
    @ApiResponses( //用于表示一组响应
            //用在@ApiResponses中，一般用于表达一个错误的响应信息
            {
                    @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "请求参数错误", response = Result.class),
                    @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "服务器内部错误", response = Result.class)
            }
    )
    public Result getSmsCode(@RequestBody @Valid SmsCodeQuery smsCodeQuery ){
        // 产生验证码
        String smsCode = "8888"; //SmsUtils.genSmsCode();
        // 发送验证码
        // 短信接口
        // 存入 redis
        redisUtils.set(smsCodeQuery.getPhone(),smsCode,smsUtils.getExpire());
        return Result.success(ResultCodeEnum.CAPTCHA_SUCCESS);
    }



    @AnonymousPostMapping(value = "/regist")
    public Result regiest(){

    }
}
