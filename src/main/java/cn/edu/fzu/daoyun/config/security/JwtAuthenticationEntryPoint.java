package cn.edu.fzu.daoyun.config.security;

import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println(e.getClass());
        if(e==null){
            SendError(response, Result.failure(ResultCodeEnum.LOGIN_FAILURE));
        }else if(e.getClass() == DisabledException.class){
            SendError(response, Result.failure(ResultCodeEnum.LOGIN_DISABLED));
        }else if(e.getClass() == BadCredentialsException.class){
            SendError(response, Result.failure(ResultCodeEnum.LOGIN_INVALID));
        }else if(e.getClass() == UsernameNotFoundException.class){
            SendError(response, Result.failure(ResultCodeEnum.LOGIN_UNEXIST));
        }
        else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }



    public void SendError(HttpServletResponse response,Result result) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(result));
        out.flush();
    }





}
