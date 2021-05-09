package cn.edu.fzu.daoyun.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class SchoolDO implements Serializable {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "学校名称",example = "福州大学", dataType="String")
    private String schName;
    @ApiModelProperty(value = "学校代码",example = "10386", dataType="Integer")
    private Integer schCode;
    @ApiModelProperty(value = "学校说明",example = "福州市闽侯县乌龙江大道2号", dataType="String")
    private String schInfo;
}
