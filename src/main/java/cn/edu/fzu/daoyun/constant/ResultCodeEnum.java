package cn.edu.fzu.daoyun.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS(2000,"successful"),
    CAPTCHA_SUCCESS(2002,"验证码获取成功"),
    LOGIN_SUCCESS(2001,"登录成功"),


    LOGIN_INVALID(4001,"用户名或密码错误"),
    LOGIN_DISABLED(4002,"账户被禁用"),
    LOGIN_LOCKED(4003,"账户被锁定"),
    LOGIN_FAILURE(4004,"登录失败"),
    CAPTCHA_FAILURE(4500,"验证码获取失败"),
    INVALID_PARAM(4501,"请求参数错误"),
    CAPTCHA_EXPIRED(4502,"验证码过期"),
    CAPTCHA_INVAILD(4503,"验证码错误"),
    CODE_INVAILD(4505,"短信验证码错误"),
    CODE_EXPIRED(4506,"短信验证码过期"),
    TOKEN_INVALID(4504,"无效的token")
    ;
    //响应状态码
    private Integer code;
    //响应信息
    private String message;
}
