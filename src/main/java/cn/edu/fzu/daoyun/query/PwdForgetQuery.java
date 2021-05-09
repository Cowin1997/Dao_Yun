package cn.edu.fzu.daoyun.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PwdForgetQuery {
    @ApiModelProperty(value = "电话号码",required = true)
    @NotNull
    private String phone;
    @ApiModelProperty(value = "短信验证码",required = true)
    @NotNull
    private String smsCode;
    @ApiModelProperty(value = "新密码",required = true)
    @NotNull
    private String newPass;

}
