package cn.codenest.springsecurityoauthqq.registrations.qq;

import cn.codenest.springsecurityoauthqq.utils.TextHtmlHttpMessageConvert;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/6 19:29
 * @description：
 * @modified By：
 * @version: $
 */
public class QQOAuth2AccessTokenResponseClient implements
        OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private RestTemplate restTemplate;

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new TextHtmlHttpMessageConvert());
            return restTemplate;
        }
        return restTemplate;
    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest oAuth2AuthorizationCodeGrantRequest) {
        ClientRegistration clientRegistration = oAuth2AuthorizationCodeGrantRequest.getClientRegistration();
        OAuth2AuthorizationExchange oAuth2AuthorizationExchange = oAuth2AuthorizationCodeGrantRequest.getAuthorizationExchange();
        //根据API文档获取请求access_token参数
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        return null;
    }
}
