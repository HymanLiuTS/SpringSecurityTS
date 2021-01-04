package cn.codenest.springsecurityts.config;

import lombok.Data;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/30 11:28
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private String imageCode;

    private boolean imageCodeIsRight;

    public boolean getImageCodeIsRight() {
        return this.imageCodeIsRight;
    }

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        imageCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String saveCode = (String) session.getAttribute("captcha");
        if (!StringUtils.isEmpty(saveCode)) {
            session.removeAttribute("captcha");
        }
        if (StringUtils.isEmpty(imageCode) || StringUtils.isEmpty(saveCode) || !imageCode.equals(saveCode)) {
            this.imageCodeIsRight = false;
        }
        this.imageCodeIsRight = true;
    }
}
