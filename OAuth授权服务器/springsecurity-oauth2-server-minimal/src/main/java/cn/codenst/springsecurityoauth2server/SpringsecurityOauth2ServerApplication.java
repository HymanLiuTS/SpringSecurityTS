package cn.codenst.springsecurityoauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/*
 * 使用配置文件application.yml配置的OAuth认证服务器
 */
@Profile("minimal")
@SpringBootApplication
@EnableAuthorizationServer
public class SpringsecurityOauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityOauth2ServerApplication.class, args);
    }

}
