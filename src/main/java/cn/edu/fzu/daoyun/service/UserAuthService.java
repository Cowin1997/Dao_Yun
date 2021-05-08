package cn.edu.fzu.daoyun.service;

import cn.edu.fzu.daoyun.dto.UserDTO;
import cn.edu.fzu.daoyun.entity.UserAuthDO;
import io.swagger.models.auth.In;

public interface UserAuthService {
    public UserDTO getByIdentifier(String identifier);
}
