package cn.codenest.springsecurityoauthqq.registrations.qq;

import cn.codenest.springsecurityoauthqq.utils.JacksonFromTextHtmlHttpMessageConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/7 10:29
 * @description：OAuth2UserService 负责请求用户信息（OAuth2User）。标准的 OAuth 可以直接携带access_token
 * 请求用户信息，但QQ需要获取到OpenId才能使用。
 * @modified By：
 * @version: $
 */
public class QQAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String QQ_URL_GET_USER_INFO =
            "http://graph.qq.com/user/get_user_info?oauth_consumer_key={appId}&openid={openId}&access_token={access_token}";

    private RestTemplate restTemplate;

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new JacksonFromTextHtmlHttpMessageConverter());
            return restTemplate;
        }
        return restTemplate;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        //第一步： 获取openId接口响应
        String accessToken = oAuth2UserRequest.getAccessToken().getTokenValue();
        String openIdUrl = oAuth2UserRequest.getClientRegistration().
                getProviderDetails().getUserInfoEndpoint().getUri()
                + "?access_token={accessToken}";
        String result = getRestTemplate().getForObject(openIdUrl, String.class, accessToken);
        //提取Openid
        String openId = result.substring(result.lastIndexOf(":\"") + 2, result.indexOf("\"}"));
        //第二步：获取用户信息
        String appId = oAuth2UserRequest.getClientRegistration().getClientId();
        QQUser qqUser = getRestTemplate().getForObject(QQ_URL_GET_USER_INFO, QQUser.class, appId, openId, accessToken);
        //为用户信息类补充openId
        if (qqUser != null) {
            qqUser.setOpenid(openId);
        }
        return qqUser;
    }
}
