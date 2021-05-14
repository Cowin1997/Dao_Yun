package cn.edu.fzu.daoyun.entity;

import cn.edu.fzu.daoyun.base.BaseDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"handler"})
public class SysDictDO extends BaseDO {

    @ApiModelProperty(value = "字典码")
    private String code;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "详情")
    private String detail;
    @ApiModelProperty(value = "字典项数据值")
    private Integer value;

    @ApiModelProperty(value = "所属类型")
    private Integer parent_id;

    @ApiModelProperty(value = "创建者ID")
    private Integer creator;
    @ApiModelProperty(value = "修改者ID")
    private Integer reviser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    @ApiModelProperty(value = "添加时间")
    private Date gmt_create;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    @ApiModelProperty(value = "添加时间")
    private Date gmt_modified;



}
