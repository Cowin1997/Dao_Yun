package cn.edu.fzu.daoyun.entity;

import cn.edu.fzu.daoyun.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel(value="Student",description="Student" )
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StudentDO  extends BaseDO implements Serializable {
    private Integer id;
    private Integer user_id;
    private Integer sid;
    private String name;
    @Size(max = 1)
    private String gender;
    private String phone;
    private Integer school_code;
    private Integer college_code;
    private Integer major_code;

}
