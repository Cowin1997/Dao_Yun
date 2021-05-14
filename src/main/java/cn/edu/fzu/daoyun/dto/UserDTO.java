package cn.edu.fzu.daoyun.dto;

import cn.edu.fzu.daoyun.entity.RoleDO;
import cn.edu.fzu.daoyun.entity.UserAuthDO;
import cn.edu.fzu.daoyun.entity.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
    private RoleDO userRole;
    // 个人信息(教师或学生)
    private Object info;
    //UserPermission信息
    private List<MenuDTO> menus;
}
