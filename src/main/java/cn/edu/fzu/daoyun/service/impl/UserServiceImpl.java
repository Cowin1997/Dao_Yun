package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.dto.UserDTO;
import cn.edu.fzu.daoyun.entity.UserAuthDO;
import cn.edu.fzu.daoyun.entity.UserDO;
import cn.edu.fzu.daoyun.mapper.UserAuthMapper;
import cn.edu.fzu.daoyun.mapper.UserMapper;
import cn.edu.fzu.daoyun.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserAuthMapper userAuthMapper;
    @Resource
    private UserMapper userMapper;

    /**
     *  根据 id 查询用户基本信息
     */
    @Override
    public UserDO getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    /*
     *  根据 identifier 查询详细的用户信息
     */
    @Override
    public UserDTO getUserDetailByIdentifier(String identifier) {
        UserAuthDO userAuth = userAuthMapper.selectByIdentifier(identifier);
        if(userAuth==null) return null;
        UserDO user = userMapper.selectById(userAuth.getUser_id());
        return new UserDTO(user,userAuth);
    }

}
