package cn.edu.fzu.daoyun.controller;

import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPostMapping;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.query.SmsCodeQuery;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import cn.edu.fzu.daoyun.utils.SmsUtils;
import cn.edu.fzu.daoyun.utils.captcha.CaptChaUtils;
import cn.edu.fzu.daoyun.vo.CaptchaVO;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "图像验证码和短信验证码接口")
@RequestMapping("/api/code")
public class VerifyController {
    private final SmsUtils smsUtils;
    private final CaptChaUtils captChaUtils;
    private final RedisUtils redisUtils;



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


}
