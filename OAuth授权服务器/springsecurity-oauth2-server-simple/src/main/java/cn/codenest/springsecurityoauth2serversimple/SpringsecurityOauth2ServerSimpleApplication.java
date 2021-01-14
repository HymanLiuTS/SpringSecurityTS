package cn.codenest.springsecurityoauth2serversimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/*
 * OAuth认证服务器和资源服务器集成在一个服务中
 */
@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
@Profile("simple")//@Profile指定只有在simple环境下才会被注册到spring容器中
public class SpringsecurityOauth2ServerSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityOauth2ServerSimpleApplication.class, args);
    }

}
