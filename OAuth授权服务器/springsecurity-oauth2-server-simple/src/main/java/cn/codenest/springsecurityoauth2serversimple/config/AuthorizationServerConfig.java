package cn.codenest.springsecurityoauth2serversimple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/8 10:13
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
@Profile("simple")
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * 配置授权服务器的安全，意味着/oauth/token端点和/oauth/authorize端点都应该是安全的
     * 默认的设置副高了绝大多数的需求，所以一般情况下不需要做任何事情
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //clientid
                .withClient("client-for-server")
                //cliet_secret
                .secret(passwordEncoder.encode("client-for-server"))
                //改client支持的授权模式。OAuth的Client在请求code时，只有传递授权模式参数，该处包含的授权模式才可以访问
                .authorizedGrantTypes("authorization_code", "implicit","password")
                //该client分配的access_token的有效时间要少于刷新时间
                //超过有效时间，但在可刷新时间范围内的access_token也可以刷新
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(72000)
                //重定向url，由客户端填写，服务器携带授权码会重定向该url
                .redirectUris("http://localhost:18889/login/oauth2/code/authorizationserver", "http://127.0.0.1:18889/login/oauth2/code/authorizationserver", "http://172.17.6.75:18889/login/oauth2/code/authorizationserver")
                .additionalInformation()
                //该client可以访问的资源服务器ID，每个资源服务器都有一个ID
                .resourceIds(ResourceServerConfig.RESOURCE_ID, "resouirceserver")
                //该Client拥有的权限，资源服务器可以根据该处订的权限对client进行鉴权
                .authorities("ROLE_CLIENT")
                //该client可以访问的资源的范围，资源服务器可以根据该处定义的方位对client进行鉴权
                .scopes("profile", "email", "phone", "aaa")
                //自动批准的范围（scope）,自动批准的scope在批准页不需要显示，即不需要用户确认批准。如果所有的scope都自动
                //批准，则不显示批准页
                .autoApprove("profile");
        //todo 这里可以设置客户端信息存储到数据库里面
        //clients.jdbc(dataSource());
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    /*
     * 该方法是用来配置授权服务器特性的（Authorization Server endpoints ）,主要是一些非安全的特性，比如
     * token存储、token的定义、授权模式等等
     * 默认不需要任何配置，如果需要密码授权，则需要提供一个AuthenticationManager
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        //todo 这里可以设置jdbcTokenStore，将产生的token放到数据库中，默认存到内存中;管理token存储位置的抽象接口是
        //TokenStore，具体实现类有 InMemoryTokenStore、JdbcTokenStore、JwtTokenStore 和RedisTokenStore\        //endpoints.tokenStore(jdbcTokenStore())
    }


}
