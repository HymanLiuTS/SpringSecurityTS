package cn.codenest.springsecurityts.config;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/31 9:20
 * @description：
 * @modified By：
 * @version: $
 */
public class MyTokenRepository implements PersistentTokenRepository {

    @Override
    public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {

    }

    @Override
    public void updateToken(String s, String s1, Date date) {

    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String s) {
        return null;
    }

    @Override
    public void removeUserTokens(String s) {

    }
}
