package cn.codenest.springsecurityts.config;

import cn.codenest.springsecurityts.exception.VerificationCodeException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/30 9:19
 * @description：
 * @modified By：
 * @version: $
 */
//OncePerRequestFilter过滤器可以保证一次请求只会通过一次该过滤器
//过滤器属于servlet层面的东西
//todo 实现图形验证码的方法一，使用自定义过滤器VerifivationCodeFilter
//@Component
public class VerifivationCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //这里做了限制，只有hello接口需要携带验证码信息
        if (!"/app/api/hello".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            verificationCode(request);
            filterChain.doFilter(request, response);
        }
    }

    public void verificationCode(HttpServletRequest request) throws VerificationCodeException {
        String requestCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String saveCode = (String) session.getAttribute("captcha");
        if (!StringUtils.isEmpty(saveCode)) {
            session.removeAttribute("captcha");
        }
        if (StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(saveCode) || !requestCode.equals(saveCode)) {
            throw new VerificationCodeException();
        }
    }
}
