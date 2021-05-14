package cn.edu.fzu.daoyun.entity;

import cn.edu.fzu.daoyun.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDO extends BaseDO implements Serializable {
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "角色级别")
    private Integer level;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "创建者")
    private Integer creator;
    @ApiModelProperty(value = "更新者")
    private Integer modifier;


}
