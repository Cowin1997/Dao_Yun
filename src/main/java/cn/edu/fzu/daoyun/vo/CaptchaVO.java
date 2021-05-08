package cn.edu.fzu.daoyun.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@ApiModel(description = "图形验证码")
public class CaptchaVO {
    @ApiModelProperty(notes = "验证码的base64编码")
    private String img;
    @ApiModelProperty(notes = "UUID")
    private String uuid;
}
