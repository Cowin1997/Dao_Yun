package cn.edu.fzu.daoyun.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"handler"})
public class SysParamDO {
    @ApiModelProperty(value = "id,添加更新不需要该字段")
    private Integer id;
    @ApiModelProperty(value = "系统参数键")
    private String arg_key;
    @ApiModelProperty(value = "系统参数值")
    private String arg_value;
    @ApiModelProperty(value = "系统参数说明")
    private String arg_desc;
    @ApiModelProperty(value = "创建者,添加更新不需要该字段")
    private Integer creator;
    @ApiModelProperty(value = "修改者,添加更新不需要该字段")
    private Integer reviser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date gmt_create;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date gmt_modified;
}
