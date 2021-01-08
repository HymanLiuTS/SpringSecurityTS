package cn.codenest.springsecurityoauth2serversimple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/8 11:17
 * @description：
 * @modified By：
 * @version: $
 */
@Profile("simple")
@Configuration
//指定本服务为资源服务器
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);
    public static final String RESOURCE_ID = "authorizationserver";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.info("ResourceServerConfig中配置HttpSecurity对象执行");
        //只有/me端点作为资源服务器的资源
        http.requestMatchers().antMatchers("/me")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
