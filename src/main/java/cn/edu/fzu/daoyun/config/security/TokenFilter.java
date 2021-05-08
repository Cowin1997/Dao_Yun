package cn.edu.fzu.daoyun.config.security;

import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.dto.JwtUserDTO;
import cn.edu.fzu.daoyun.service.OnlineUserService;
import cn.edu.fzu.daoyun.service.impl.OnlineUserServiceImpl;
import cn.edu.fzu.daoyun.utils.JwtUtils;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *  Token过滤器,受保护资源需要请求头中携带token
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class TokenFilter extends GenericFilterBean {
    private final SecurityProperties properties;
    private final OnlineUserServiceImpl onlineUserService;
    private final JwtUtils jwtUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = this.resolveToken((HttpServletRequest) request);
        if(token!=null){ // 检验 token
            // 到 redis 获取 token
            JwtUserDTO jwtUserDTO = onlineUserService.getOne(properties.getOnlineKey() + token);
            if(jwtUserDTO==null){
                // 无效的 token
                sendError((HttpServletResponse) response,Result.failure(ResultCodeEnum.TOKEN_INVALID));
            }else{ //有效token
               // token 续期

            }

        }else{
            filterChain.doFilter(request, response); //放行继续下一个Filter
        }
    }


    /**
     * 初步检测Token
     *
     * @param request /
     * @return /
     */
    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(properties.getHeader());
        return token;
    }

    public void sendError(HttpServletResponse response, Result result) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(result));
        out.flush();
    }
}
