package cn.edu.fzu.daoyun.utils.captcha;

import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.exception.BadConfigurationException;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.awt.*;
import java.util.Objects;

/**
 *  验证码工具类
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class CaptChaUtils {
    // 自动注入
    private final CaptchaProperties captchaProperties;
    private final RedisUtils redisUtils;


    /**
     *  获取验证码过期时间
     */
    public Long getExpiration(){
        return this.captchaProperties.getExpiration();
    }

    /**
     * 获取验证码生产类
     */
    public Captcha getCaptcha() {
        if (Objects.isNull(captchaProperties.getCaptchaType())) {
            //默认为算数类验证码
            captchaProperties.setCaptchaType(CaptchaEnum.arithmetic);
        }
        return switchCaptcha(captchaProperties);
    }

    /**
     * 依据配置信息生产验证码
     * @param captchaProperties 验证码配置信息
     */
    private Captcha switchCaptcha(CaptchaProperties captchaProperties) {
        Captcha captcha;
        synchronized (this) {
            switch (captchaProperties.getCaptchaType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(captchaProperties.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    captcha.setLen(captchaProperties.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    captcha.setLen(captchaProperties.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    captcha.setLen(captchaProperties.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                    captcha.setLen(captchaProperties.getLength());
                    break;
                default:
                    throw new BadConfigurationException("验证码配置信息错误！正确配置查看 cn.edu.fzu.daoyun.utils.captcha.CaptchaEnum ");
            }
        }
        if(!StringUtils.isEmptyOrWhitespace(captchaProperties.getFontName())){
            captcha.setFont(new Font(captchaProperties.getFontName(), Font.PLAIN, captchaProperties.getFontSize()));
        }
        return captcha;
    }

    public Result checkValid(String uuid, String validCode){
        // 查询图片验证码
        String code = (String) redisUtils.get(uuid);
        // 清除 Redis 验证码
        // redisUtils.del(uuid);
        // 验证码过期
        if (io.micrometer.core.instrument.util.StringUtils.isBlank(code)) {
            return Result.failure(ResultCodeEnum.CAPTCHA_EXPIRED);
        }
        // 验证码错误
        if (io.micrometer.core.instrument.util.StringUtils.isBlank(validCode) || !validCode.equalsIgnoreCase(code)) {
            return Result.failure(ResultCodeEnum.CAPTCHA_INVAILD);
        }
        return Result.success(ResultCodeEnum.SUCCESS);
    }


}
