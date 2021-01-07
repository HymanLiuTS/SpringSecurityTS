package cn.codenest.springsecurityoauthqq.config;

import cn.codenest.springsecurityoauthqq.registrations.composite.CompositeOAuth2AccessTokenResponseClient;
import cn.codenest.springsecurityoauthqq.registrations.composite.CompositeOAuth2UserService;
import cn.codenest.springsecurityoauthqq.registrations.qq.QQAuth2UserService;
import cn.codenest.springsecurityoauthqq.registrations.qq.QQOAuth2AccessTokenResponseClient;
import cn.codenest.springsecurityoauthqq.registrations.qq.QQUser;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 9:43
 * @description：
 * @modified By：
 * @version: $
 */
//EnableWebSecurity注解已经包含Configuration
//WebSecurityConfigurerAdapter实际上是过滤器
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String QQRegistrationId = "qq";
    public static final String WebChatRegistrationId = "wechat";
    public static final String LoginPagePath = "/login/oauth2";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(LoginPagePath).permitAll()
                .anyRequest()
                .authenticated();

        http.oauth2Login()
                .loginPage("/login/oauth2")
                .tokenEndpoint()
                .accessTokenResponseClient(this.accessTokenResponseClient())
                .and()
                .userInfoEndpoint()
                //注册自定义的用户类
                .customUserType(QQUser.class, QQRegistrationId)
                .userService(oauth2UserService())
                .and()
                .redirectionEndpoint().baseUri("/register/social/*");

        http.oauth2Login().loginPage(LoginPagePath);

    }

    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        CompositeOAuth2AccessTokenResponseClient client = new CompositeOAuth2AccessTokenResponseClient();
        //加入QQ的QQOAuth2AccessTokenResponseClient
        client.getOAuth2AccessTokenResponseClients().put(QQRegistrationId, new QQOAuth2AccessTokenResponseClient());
        return client;
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        CompositeOAuth2UserService service = new CompositeOAuth2UserService();
        service.getUserServices().put(QQRegistrationId, new QQAuth2UserService());
        return service;
    }


}
