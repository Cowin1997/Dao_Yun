package cn.edu.fzu.daoyun.entity;

import cn.edu.fzu.daoyun.base.BaseDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ApiModel
@Data
@ToString
@NoArgsConstructor
public class CourseDO extends BaseDO implements Serializable {
    @ApiModelProperty(value = "班课ID")
    private Integer cid;
    @ApiModelProperty(value = "教师教工号ID")
    private Integer teacher_tid;
    @ApiModelProperty("是否可以加入")
    private Boolean enabled;
    @ApiModelProperty("班课名")
    private String coursename;
    @ApiModelProperty("班课描述")
    private String detailinfo;
    @ApiModelProperty("所属学校编码")
    private Integer school_code;
    @ApiModelProperty("所属学院编码")
    private Integer college_code;
    @ApiModelProperty("所属专业编码")
    private Integer major_code;


}
