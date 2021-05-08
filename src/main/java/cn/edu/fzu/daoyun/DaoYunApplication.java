package cn.edu.fzu.daoyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(value = "cn.edu.fzu.daoyun.mapper")
@EnableConfigurationProperties
public class DaoYunApplication {
    public static void main(String[] args) {
        SpringApplication.run(DaoYunApplication.class, args);
    }

}
