package cn.edu.fzu.daoyun.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class FastRegisterQuery {
    @NotNull
    private String phone;
    @NotNull
    private String smsCode;
}
