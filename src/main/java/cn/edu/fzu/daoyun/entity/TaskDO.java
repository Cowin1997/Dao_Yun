package cn.edu.fzu.daoyun.entity;

import cn.edu.fzu.daoyun.base.BaseDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ApiModel
@Data
@ToString
public class TaskDO extends BaseDO implements Serializable {
    @ApiModelProperty(value = "签到任务班级ID")
    private Integer course_cid;

    @ApiModelProperty(value = "发起签到者经度")
    private Double longitude;


    @ApiModelProperty(value = "发起签到类型 1限时签到 2 手势签到")
    private Integer type;

    @ApiModelProperty(value = "发起签到者维度")
    private Double latitude;

    @ApiModelProperty(value = "签到任务是否结束")
    private Boolean disabled;
    @ApiModelProperty(value = "限时时间")
    private Integer time_limit;

    @ApiModelProperty(value = "手势密码")
    private String password;
}
