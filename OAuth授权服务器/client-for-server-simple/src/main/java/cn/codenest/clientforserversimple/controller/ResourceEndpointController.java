package cn.codenest.clientforserversimple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/11 14:56
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
public class ResourceEndpointController {

    private static final String URL_GET_USER_PHONE = "http://172.17.6.75:9998/phone";

    private static final String URL_GET_USER_RES = "http://172.17.6.75:9090/resource";

    @Autowired
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;

    private RestTemplate restTemplate;

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }

    @GetMapping("/phone")
    public String userInfo(OAuth2AuthenticationToken authenticationToken) {
        OAuth2AuthorizedClient auth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient(
                authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getName()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + auth2AuthorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> respon = getRestTemplate().exchange(URL_GET_USER_PHONE, HttpMethod.GET, requestEntity, String.class);
        return respon.getBody();
    }

    @GetMapping("/resource")
    public String resource(OAuth2AuthenticationToken authenticationToken) {
        OAuth2AuthorizedClient auth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient(
                authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getName()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + auth2AuthorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> respon = getRestTemplate().exchange(URL_GET_USER_RES, HttpMethod.GET, requestEntity, String.class);
        return respon.getBody();
    }
}
