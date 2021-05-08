package cn.edu.fzu.daoyun.utils;

import cn.edu.fzu.daoyun.dto.JwtUserDTO;
import cn.edu.fzu.daoyun.dto.UserDTO;
import cn.edu.fzu.daoyun.entity.UserDO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "daoyun.jwt")
@Slf4j
public class JwtUtils {

    private String SECRET;
    private long EXPIRE;


    public String generate(UserDO user){
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        try {
            String token = JWT.create()
                    // 添加头部
                    .withHeader(map)
                    // 放入用户的id
                    .withAudience(String.valueOf(user.getId()))
                    // 可以将基本信息放到claims中
                    .withClaim("username", user.getNickname())
                    .withClaim("type", String.valueOf(user.getRole_id()))
                    // 超时设置,设置过期的日期
                 //   .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE))
                    // 签发时间
                    .withIssuedAt(new Date())
                    // SECRET加密
                    .sign(Algorithm.HMAC256(SECRET));
            return token;
        }catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Boolean checkValid(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e){
            log.info(e.getMessage());
            return false;
        }
    }
}
