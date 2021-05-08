package cn.edu.fzu.daoyun.entity;

import cn.edu.fzu.daoyun.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;



@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDO extends BaseDO implements Serializable {
    @NotNull
    private Integer user_id; //对应User表的id
    @Size(max = 32, message = "最大长度为32")
    private String identity_type; //授权类型
    @Size(max = 32, message = "最大长度为32")
    private String identifier; //账户(电话号码,邮箱地址,第三方用户名[QQ号])
    @Size(max = 255, message = "最大长度为255")
    private String credential; //凭据(inner:密码,其他:token)

}
