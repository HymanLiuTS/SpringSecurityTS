package cn.codenest.springsecurityoauthqq.registrations.qq;

import cn.codenest.springsecurityoauthqq.utils.TextHtmlHttpMessageConvert;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/6 19:29
 * @description：OAuth2AccessTokenResponseClient实现了以code交换access_token的具体逻辑。默认提供的实现类
 * NimbusAuthorizationCodeTokenResponseClients 可以实现标准的 OAuth 交换access_token 的具体逻辑，
 * 但 QQ 提供的方式并不标准，所以需要自定义实现OAuth2AccessTokenResponseClient接口。
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
        params.set("client_id", clientRegistration.getClientId());
        params.set("client_secret", clientRegistration.getClientSecret());
        params.set("code", oAuth2AuthorizationExchange.getAuthorizationResponse().getCode());
        params.set("redirect_uri", oAuth2AuthorizationExchange.getAuthorizationRequest().getAuthorizationUri());
        params.set("grant_type", "authorization_code");
        String tmpTokenReponse = getRestTemplate().postForObject(clientRegistration.getProviderDetails().getTokenUri(), params, String.class);
        //从API文档中可以获知解析accessToken的方式
        String[] items = tmpTokenReponse.split("&");
        String accessToken = items[0].substring(items[0].lastIndexOf("=") + 1);
        Long expiresIn = new Long(items[1].substring(items[0].lastIndexOf("=") + 1));
        Set<String> scopes = new LinkedHashSet<>(oAuth2AuthorizationExchange.getAuthorizationRequest().getScopes());
        Map<String, Object> additionalParameters = new LinkedHashMap<>();
        OAuth2AccessToken.TokenType accessTokenType = OAuth2AccessToken.TokenType.BEARER;
        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(accessTokenType)
                .expiresIn(expiresIn)
                .scopes(scopes)
                .additionalParameters(additionalParameters)
                .build();
    }
}
