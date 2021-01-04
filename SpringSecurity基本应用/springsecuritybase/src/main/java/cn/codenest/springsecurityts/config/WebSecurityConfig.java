package cn.codenest.springsecurityts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 9:43
 * @description：
 * @modified By：
 * @version: $
 */
//EnableWebSecurity注解已经包含Configuration
//WebSecurityConfigurerAdapter实际上是过滤器
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails>  myWebAuthenticationDetailsSource;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private SpringSessionBackedSessionRegistry sessionRegistry;


    //todo 方法3，使用自定义的service，三个方法取其1
    @Autowired(required = false)
    UserDetailsService userDetailsService;

    //针对全局进行配置
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO 关闭spring security
        //web.ignoring().antMatchers("/**");
    }

    //TODO 配置用户的方法一
    //针对用户进行配置
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(User.withUsername("user").password("123456").roles("USER").build());
        auth.authenticationProvider(authenticationProvider);
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

    @Override
    //这个方法是spring security过滤请求的第一道门卡，针对http路径进行配置
    protected void configure(HttpSecurity http) throws Exception {

        InMemoryTokenRepositoryImpl inMemoryTokenRepository = new InMemoryTokenRepositoryImpl();

        //todo 登录的设置
        http.authorizeRequests()
                .antMatchers("/admin/api/*").hasAuthority("ADMIN")//使用hasAuthority时，用户的角色前面不用加ROLE_前缀
                .antMatchers("/user/api/*").hasRole("USER")//使用hasRole时，用户的角色前面要加ROLE_前缀
                .antMatchers("/app/api/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .authenticationDetailsSource(myWebAuthenticationDetailsSource)
                .permitAll()
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService)//这里可以显示指定用户服务
                //.key("mykey")//todo 用散列算法加密用户必要的登录信息并生成令牌。这是设置的是将用户名、密码、过期时间加密时的盐值，否则默认是随机生成的字符串，每次重启服务都会改变
                .tokenRepository(inMemoryTokenRepository)//todo 使用数据库等持久性数据存储机制用的持久化令牌。
                .and()
                .csrf()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())//添加csrftoken仓库
                .disable()//禁用csrf防护策略
                .sessionManagement()//sessionManagement用来管理用户的会话，
                .sessionFixation().none()//设置防御会话固定攻击的策略为newSession，其他还有none，migrateSession，changeSessionId
                .invalidSessionUrl("/session/invalid")//设置会话过期后跳转的url
                .invalidSessionStrategy(new InvalidSessionStrategy() {//设置会话过期策略
                    @Override
                    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

                    }
                })
                .maximumSessions(10)
                .maxSessionsPreventsLogin(false)//true,当会话达到最大数时可以阻止新会话的建立
                .sessionRegistry(sessionRegistry);

        //todo 注销的设置
        http.logout()
                //指定注销的路由
                .logoutUrl("/logout")
                //注销成功后重定向的路由
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        System.out.println("注销成功");
                    }
                })
                //使改用户的HttpSession失效
                .invalidateHttpSession(true)
                //删除指定的cookie
                .deleteCookies("cookie1", "cookie2")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                        System.out.println("logout");
                    }
                });

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


    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration=new CorsConfiguration();
        //允许从百度跨域访问
        configuration.setAllowedOrigins(Arrays.asList("https://www.baidu.com"));
        //允许使用GET、POST方法
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        //允许带凭证
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        //对所有的url生效
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

}
