package cn.edu.fzu.daoyun.service;

import cn.edu.fzu.daoyun.dto.UserDTO;
import cn.edu.fzu.daoyun.entity.UserDO;

public interface UserService {
    public UserDO getUserById(Integer id);
    public UserDTO getUserDetailByIdentifier(String identifier);
}
