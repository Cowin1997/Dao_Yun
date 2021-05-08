package cn.edu.fzu.daoyun.config.security;

import cn.edu.fzu.daoyun.annotation.AnonymousAccess;
import cn.edu.fzu.daoyun.constant.RequestMethodEnum;
import cn.edu.fzu.daoyun.service.impl.SecurityUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // final类型+@RequiredArgsConstructor 会自动注入
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;
    private final SecurityUserDetailsServiceImpl securityUserDetailsService;
    private final TokenConfigurer tokenConfigurer;
    private final SmSAuthenticationProvider smSAuthenticationProvider;
    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }




    /**默认的加密方式,加盐,看源码可以手动加盐,如果没有则程序自定义盐,并将盐以一定的规律存放在生成的密码中,
     * 同一个密码两次加密是不同的,而security验证的时候比较加密之后字符串,所以要通过盐的辅助比较密码
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 默认的 DaoAuthenticationProvider
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(this.getPasswordEncoder());
        // 自定义认证器
        auth.authenticationProvider(smSAuthenticationProvider);
    }

    /**
     * 安全拦截配置
     * 通常,子类不能通过调用 super 来调用此方法，因为它可能会覆盖其配置。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            获取请求对应的ur和方法情况
            {POST [/login]}=cn.edu.fzu.daoyun.controller.LoginController#login()
         */
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // 获取匿名标记
        Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);

        http.csrf().disable(); // 禁用 CSRF
        http.exceptionHandling()   // 授权异常
                .authenticationEntryPoint(authenticationErrorHandler) // 没有通过认证的情况下访问受保护的资源
                .accessDeniedHandler(jwtAccessDeniedHandler)   //没有通过授权的情况下访问受保护的资源
        .and()  // 不创建会话
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and() // 拦截配置
                .authorizeRequests()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 自定义匿名访问所有url放行:允许匿名和带Token访问,细腻化到每个 Request 类型
                // GET
                .antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll()
                // POST
                .antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).permitAll()
                // PUT
                .antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).permitAll()
                // PATCH
                .antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).permitAll()
                // DELETE
                .antMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).permitAll()
                // 所有类型的接口都放行
                .antMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0])).permitAll()
                .anyRequest().authenticated() //所有请求都需要认证
        .and()
                .apply(tokenConfigurer); // 添加 token 检验过滤
    }


    /**
     *  获取匿名标记
     */
    private Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Map<String, Set<String>> anonymousUrls = new HashMap<>(6);
        Set<String> get = new HashSet<>();
        Set<String> post = new HashSet<>();
        Set<String> put = new HashSet<>();
        Set<String> patch = new HashSet<>();
        Set<String> delete = new HashSet<>();
        Set<String> all = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) { //拥有@anonymousAccess注解
                List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                RequestMethodEnum request = RequestMethodEnum.find(requestMethods.size() == 0 ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());
                switch (Objects.requireNonNull(request)) {
                    case GET:
                        get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case POST:
                        post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PUT:
                        put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PATCH:
                        patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case DELETE:
                        delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    default:
                        all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                }
            }
        }
        anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
        anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
        return anonymousUrls;
    }
}
