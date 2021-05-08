package cn.edu.fzu.daoyun.service;

import cn.edu.fzu.daoyun.dto.JwtUserDTO;



public interface OnlineUserService {
    /**
     * 保存在线用户信息
     */
    public void save(JwtUserDTO jwtUserDto, String token);
    /**
     * 退出登录
     */
    public void logout(String token);

    public JwtUserDTO getOne(String token);
}
