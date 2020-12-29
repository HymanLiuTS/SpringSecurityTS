package cn.codenest.springsecurityts.config;

import cn.codenest.springsecurityts.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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

    //todo 方法3，使用自定义的service，三个方法取其1
    @Autowired(required = false)
    MyUserDetailService myUserDetailService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO 关闭spring security
        //web.ignoring().antMatchers("/**");
    }

    //TODO 配置用户的方法一
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(User.withUsername("user").password("123456").roles("USER").build());
        super.configure(auth);
    }

    //TODO 配置用户的方法2，方法1和2两者取其一，否则方法2会覆盖方法1的配置
    @Override
    //@Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        //User user = new User(1L, "user", "123456", true, "USER");
        //User admin = new User(2L, "admin", "123456", true, "ADMIN");
        manager.createUser(User.withUsername("user").password("123456").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("123456").roles("ADMIN").build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();//new BCryptPasswordEncoder();
    }


    @Override
    //这个方法是spring security过滤请求的第一道门卡
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/api/*").hasAuthority("ADMIN")//使用hasAuthority时，用户的角色前面不用加ROLE_前缀
                .antMatchers("/user/api/*").hasRole("USER")//使用hasRole时，用户的角色前面要加ROLE_前缀
                .antMatchers("/app/api/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();


        //这是WebSecurityConfigurerAdapter默认的安全设置
        /*
        ((HttpSecurity) ((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.
                AuthorizedUrl) http.authorizeRequests().
                anyRequest()).authenticated().and()).//任何请求都要进行认证
                formLogin().and()).//支持表单登录，这里也可以使用loginPage进行登录页面的指定
                httpBasic();//开启httpbasic认证

         */

        //这是自定义登录页面和登录路径的配置
        /*((HttpSecurity) ((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.
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
                csrf().disable();*/
    }

}
