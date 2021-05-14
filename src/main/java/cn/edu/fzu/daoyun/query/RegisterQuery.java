package cn.edu.fzu.daoyun.query;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class RegisterQuery {
    @ApiModelProperty(required = true, value="身份类型{3:老师,4:学生}",example="3")
    @NotNull
    private Integer type;
    @NotNull
    @ApiModelProperty(required = true,value="教工号/学号",example="200327***")
    private Integer idNumber;  // 教工号
    @NotNull
    @ApiModelProperty(required = true,value="密码",example="*****")
    private String password;    // credential
    @NotNull
    @ApiModelProperty(required = true,value="用户名",example="student**")
    private String username; // identifier

    @NotNull
    @ApiModelProperty(required = true,value="手机号码",example="130***8963")
    private String phone;// 手机号码

    @NotNull
    @ApiModelProperty(required = true,value="手机号码",example="130***8963")
    private String smsCode;// 验证码





}
