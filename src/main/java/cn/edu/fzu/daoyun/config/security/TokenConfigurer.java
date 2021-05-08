package cn.edu.fzu.daoyun.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *  Token配置类(SpringSecurity)
 */
@RequiredArgsConstructor
@Configuration
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenFilter tokenFilter;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 在 UsernamePasswordAuthenticationFilter 之前拦截
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
