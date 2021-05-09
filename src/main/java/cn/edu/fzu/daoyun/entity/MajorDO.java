package cn.edu.fzu.daoyun.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MajorDO implements Serializable {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "专业名称",example = "电子信息", dataType="String")
    private String majName;
    @ApiModelProperty(value = "专业代码",example = "80701", dataType="Integer")
    private Integer majCode;
    @ApiModelProperty(value = "专业说明",example = "本专业培养具备电子信息科学与技术的基本理论和基本知识", dataType="String")
    private String majInfo;
}
