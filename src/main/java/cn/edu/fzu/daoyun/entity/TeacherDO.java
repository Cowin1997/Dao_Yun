package cn.edu.fzu.daoyun.entity;

import cn.edu.fzu.daoyun.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ApiModel
public class TeacherDO extends BaseDO implements Serializable {
    private Integer id;
    private Integer user_id;
    private Integer tid;
    private String name;
    @Size(max = 1)
    private String gender;
    private String phone;
    private Integer school_code;
    private Integer college_code;
    private Integer major_code;
}
