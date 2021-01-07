package cn.codenest.springsecurityoauthqq.registrations.composite;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/7 11:22
 * @description：
 * @modified By：
 * @version: $
 */
public class CompositeOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private Map<String, OAuth2UserService> userServices;

    private static final String DefaultUserServiceKey = "default_key";

    //DefaultOAuth2UserService是默认的从github等标准OAuth服务商读取用户信息的service
    public CompositeOAuth2UserService() {
        this.userServices = new HashMap<>();
        this.userServices.put(DefaultUserServiceKey, new DefaultOAuth2UserService());
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = oAuth2UserRequest.getClientRegistration();
        OAuth2UserService service = userServices.get(clientRegistration.getRegistrationId());
        if (service == null) {
            service = userServices.get(DefaultUserServiceKey);
        }
        return service.loadUser(oAuth2UserRequest);
    }

    public Map<String, OAuth2UserService> getUserServices() {
        return userServices;
    }
}
