package cn.edu.fzu.daoyun.utils.captcha;

import lombok.Data;
import org.springframework.context.annotation.Configuration;



/**
 * 登录验证码配置信息
 */
@Data
@Configuration
public class CaptchaProperties {

    /**
     * 验证码配置: 默认
     */
    private CaptchaEnum captchaType;
    /**
     * 验证码有效期
     */
    private Long expiration = 30000L;

    /**
     * 验证码 key 前缀
     */
    //private String codeKey = "";


    /**
     * 验证码内容长度
     */
    private int length = 3;
    /**
     * 验证码宽度
     */
    private int width = 111;
    /**
     * 验证码高度
     */
    private int height = 36;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     */
    private int fontSize = 25;

}
