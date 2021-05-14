package cn.edu.fzu.daoyun.daoyun;

import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.config.security.SecurityProperties;
import cn.edu.fzu.daoyun.dto.SysDictDTO;
import cn.edu.fzu.daoyun.entity.SysDictDO;
import cn.edu.fzu.daoyun.service.SysDictService;
import cn.edu.fzu.daoyun.utils.captcha.CaptChaUtils;
import cn.edu.fzu.daoyun.utils.JwtUtils;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DaoYunApplicationTests {
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private CaptChaUtils captChaUtils;
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private SysDictService sysDictService;
    @Test
    void contextLoads() {
        System.out.println(securityProperties.toString());
    }
    @Test
    void testRedis01(){
        redisUtils.set("key",new Date());
        System.out.println(redisUtils.get("key"));
    }
    @Test
    void testCaptcha(){
        System.out.println(captChaUtils.getCaptcha().toBase64());
    }

    @Test
    void test001(){
        Page<SysDictDTO> dictPage = sysDictService.getDictPage(0, 10);
        System.out.println(dictPage.toString());
    }

}
