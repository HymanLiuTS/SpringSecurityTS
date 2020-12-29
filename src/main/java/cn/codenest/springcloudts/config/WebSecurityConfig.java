package cn.codenest.springcloudts.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 9:43
 * @description：
 * @modified By：
 * @version: $
 */
//EnableWebSecurity注解已经包含Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    //这个方法是spring security过滤请求的第一道门卡
    protected void configure(HttpSecurity http) throws Exception {
        //这是WebSecurityConfigurerAdapter默认的安全设置
        ((HttpSecurity) ((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.
                AuthorizedUrl) http.authorizeRequests().
                anyRequest()).authenticated().and()).//任何请求都要进行认证
                formLogin().and()).//支持表单登录，这里也可以使用loginPage进行登录页面的指定
                httpBasic();//开启httpbasic认证
    }

}
