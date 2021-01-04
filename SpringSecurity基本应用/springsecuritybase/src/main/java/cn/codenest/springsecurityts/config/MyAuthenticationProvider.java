package cn.codenest.springsecurityts.config;

import cn.codenest.springsecurityts.exception.VerificationCodeException;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/30 9:51
 * @description：
 * @modified By：
 * @version: $
 */
@Component("authenticationProvider")
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    UserDetailsService userDetailsService;
    PasswordEncoder passwordEncoder;

    //构造方法中注入userDetailsService和passwordEncoder
    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    //附加认证过程
    @SneakyThrows
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)  {
        //todo 验证码验证方法二:自定义MyWebAuthenticationDetails、MyWebAuthenticationDetailsSource、MyAuthenticationProvider
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) usernamePasswordAuthenticationToken.getDetails();
        if (!details.getImageCodeIsRight()) {
            throw new VerificationCodeException();
        }
        //密码验证
        if (usernamePasswordAuthenticationToken.getCredentials() == null) {
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "密码不能为空"));
        } else {
            String password = usernamePasswordAuthenticationToken.getCredentials().toString();
            if (!password.equals(userDetails.getPassword())) {
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "密码错误"));
            }
        }
    }

    //检索用户
    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userDetailsService.loadUserByUsername(s);
    }
}
