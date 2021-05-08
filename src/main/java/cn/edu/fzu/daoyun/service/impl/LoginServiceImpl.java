package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.config.security.SmsAuthenticationToken;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.dto.JwtUserDTO;
import cn.edu.fzu.daoyun.query.LoginQuery;
import cn.edu.fzu.daoyun.service.OnlineUserService;
import cn.edu.fzu.daoyun.utils.JwtUtils;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl {
    private final AuthenticationManager authenticationManager;
    private final RedisUtils redisUtils;
    private final JwtUtils jwtUtils;
    private final OnlineUserService onlineUserService;
    /**
     *  通过用户名密码登录
     * @param loginQuery
     * @return
     */
    public String loginByLocal(LoginQuery loginQuery){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginQuery.getIdentifier(), loginQuery.getCredential());
        String token = doLogin(authenticationToken);
        return token;
    }

    public String doLogin(AbstractAuthenticationToken authenticationToken){
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 存储安全上下文(security context)的信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 登录成功
        JwtUserDTO jwtUserDTO = (JwtUserDTO) authentication.getPrincipal();
        String token = jwtUtils.generate(jwtUserDTO.getUserDTO().getUser());
        // 保存在线信息
        onlineUserService.save(jwtUserDTO, token);
        // 踢掉之前已经登录的 token
        //**
        //**
        return token;
    }

    public String loginByPhone(LoginQuery loginQuery){
        SmsAuthenticationToken authenticationToken =
                new SmsAuthenticationToken(loginQuery.getIdentifier(), loginQuery.getCredential());
        String token = doLogin(authenticationToken);
        return token;
    }


}
