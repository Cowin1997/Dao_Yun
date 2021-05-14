package cn.edu.fzu.daoyun.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class CheckQuery {
    @ApiModelProperty(value = "学生ID")
    private Integer sid;
    @ApiModelProperty(value = "所在班级")
    private Integer classId;

    private Double longitude;
    private Double latitude;

    @ApiModelProperty(hidden = true)
    private Date createTime;


}
