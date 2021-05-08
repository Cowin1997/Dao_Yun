package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.dto.JwtUserDTO;
import cn.edu.fzu.daoyun.dto.UserDTO;
import cn.edu.fzu.daoyun.entity.UserDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserServiceImpl userService;

    @Override
    public JwtUserDTO loadUserByUsername(String identifier) throws UsernameNotFoundException {
        log.info("loadUserByUsername..");
        // 用户凭证信息
        UserDTO userDTO = this.userService.getUserDetailByIdentifier(identifier);
        // UsernameNotFoundException会自动变成 BadCredentialsException
        if(userDTO==null) throw new UsernameNotFoundException("用户名不存在");
        // 内置包括检查其中的三个,用户是否锁定(LockedException)、用户是否过期(AccountExpiredException)、用户是否可用(DisabledException)。
        return new JwtUserDTO(userDTO);
    }

    public JwtUserDTO loadUserByPhone(String phone){
        log.info("loadUserByPhone..");
        UserDTO userDTO = this.userService.getUserDetailByIdentifier(phone);
        if(userDTO==null) throw new UsernameNotFoundException("手机号码未注册");
        return new JwtUserDTO(userDTO);
    }



}
