package cn.edu.fzu.daoyun.dto;

import cn.edu.fzu.daoyun.entity.UserAuthDO;
import cn.edu.fzu.daoyun.entity.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    //基础User信息
    private UserDO user;
    //UserAuth信息
    private UserAuthDO userAuth;
    //UserRole信息
    //UserPermission信息
}
