package cn.edu.fzu.daoyun.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class CollegeDO implements Serializable {
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "学院名称",example = "数学与计算机学院", dataType="String")
    private String colName;
    @ApiModelProperty(value = "学院代码",example = "1038601", dataType="Integer")
    private Integer colCode;
    @ApiModelProperty(value = "学院说明",example = "福州市闽侯县学园路2号福州大学数学与计算机科学学院", dataType="String")
    private String colInfo;
}
