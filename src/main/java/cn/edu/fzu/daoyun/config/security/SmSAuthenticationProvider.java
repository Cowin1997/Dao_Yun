package cn.edu.fzu.daoyun.config.security;

import cn.edu.fzu.daoyun.dto.JwtUserDTO;
import cn.edu.fzu.daoyun.service.impl.SecurityUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *  自定义短信验证码认证处理器
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SmSAuthenticationProvider implements AuthenticationProvider {
    private final SecurityUserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtUserDTO jwtUserDTO = userDetailsService.loadUserByPhone((String) authentication.getPrincipal());

        return createSuccessAuthentication(jwtUserDTO, authentication, jwtUserDTO); //如果返回结果为空,ProviderManager 继续调用下一个Provider
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        // 暂时不设置 authorities
        SmsAuthenticationToken result = new SmsAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(user);
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        /**
         * providerManager 会遍历所有
         * securityconfig 中注册的 provider集合
         * 根据此方法返回 true 或 false 来决定由哪个 provider
         * 去校验请求过来的 authentication
         */
        return (SmsAuthenticationToken.class.isAssignableFrom(authentication));

    }


}
