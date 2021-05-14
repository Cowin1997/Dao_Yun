package cn.edu.fzu.daoyun.vo;

import cn.edu.fzu.daoyun.entity.UserDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    private UserDO user;
    private String token;
    @ApiModelProperty(value = "学工号")
    private Integer idNumber;
}
