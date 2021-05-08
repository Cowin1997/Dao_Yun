package cn.edu.fzu.daoyun.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "登录请求参数")
public class LoginQuery {
    @NotNull
    @NotEmpty
    @ApiModelProperty(notes = "登录标识:用户名,邮箱,手机号码",required = true)
    private String identifier;
    @NotNull
    @NotEmpty
    @ApiModelProperty(notes = "登录凭证:密码,邮箱验证码,短信验证码",required = true)
    private String credential;

    @NotNull
    @ApiModelProperty(notes = "登录类型:1 用户名密码, 2 短信验证码",required = true)
    private Integer type;

    @NotNull
    @NotEmpty
    @ApiModelProperty(notes = "验证码",required = true)
    private String code;
    @NotNull
    @NotEmpty
    @ApiModelProperty(notes = "验证码UUID",required = true)
    private String uuid;

}
