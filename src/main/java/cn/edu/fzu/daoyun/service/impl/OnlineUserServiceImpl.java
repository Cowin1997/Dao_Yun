package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.config.security.SecurityProperties;
import cn.edu.fzu.daoyun.dto.JwtUserDTO;
import cn.edu.fzu.daoyun.service.OnlineUserService;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class OnlineUserServiceImpl implements OnlineUserService {
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;


    @Override
    public void save(JwtUserDTO jwtUserDto, String token) {
        redisUtils.set(properties.getOnlineKey() + token, jwtUserDto, properties.getTokenValidityInSeconds()/1000);
    }

    @Override
    public void logout(String token) {

    }

    /**
     * 查询用户
     */
    @Override
    public JwtUserDTO getOne(String key) {
        return (JwtUserDTO) redisUtils.get(key);
    }
}
