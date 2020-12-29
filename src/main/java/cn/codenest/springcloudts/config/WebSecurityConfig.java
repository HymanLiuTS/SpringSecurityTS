package cn.codenest.springcloudts.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
    public void configure(WebSecurity web) throws Exception {
        // TODO 关闭spring security
        //web.ignoring().antMatchers("/**");
    }

    @Override
    //这个方法是spring security过滤请求的第一道门卡
    protected void configure(HttpSecurity http) throws Exception {
        //这是WebSecurityConfigurerAdapter默认的安全设置
        /*
        ((HttpSecurity) ((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.
                AuthorizedUrl) http.authorizeRequests().
                anyRequest()).authenticated().and()).//任何请求都要进行认证
                formLogin().and()).//支持表单登录，这里也可以使用loginPage进行登录页面的指定
                httpBasic();//开启httpbasic认证

         */

        ((HttpSecurity) ((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.
                AuthorizedUrl) http.authorizeRequests().
                anyRequest()).authenticated().//任何请求都要进行认证
                and()).
                formLogin().
                loginPage("/Mylogin.html").//自定义请求页面,页面文件需要放在static目录下，且不需要使用thymeleaf
                loginProcessingUrl("/user/login").//自定义请求路径
                permitAll().
                //指定成功时的处理逻辑
                        successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"message\":\"欢迎登录系统\"}");
                    }
                }).     //指定失败时的处理逻辑
                failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.setStatus(401);
                PrintWriter out = httpServletResponse.getWriter();
                out.write(String.format("{\"message\":\"%s\"}", e.getMessage()));
            }
        }).
                        and()).
                csrf().disable();
    }

}
