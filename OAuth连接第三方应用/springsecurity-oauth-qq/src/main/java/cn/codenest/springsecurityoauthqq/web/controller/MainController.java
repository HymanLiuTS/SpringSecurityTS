package cn.codenest.springsecurityoauthqq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/7 14:12
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class MainController {
    @Autowired
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;

    @GetMapping("/")
    public String index(Model model, OAuth2AuthenticationToken authenticationToken) {
        OAuth2AuthorizedClient auth2AuthorizedClient = this.getAuthorizedClient(authenticationToken);
        model.addAttribute("userName",authenticationToken.getName());
        model.addAttribute("clientName",auth2AuthorizedClient.getClientRegistration().getClientName());
        return "index";
    }

    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authenticationToken) {
        return this.auth2AuthorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
    }

    @GetMapping("/login/oauth2")
    public String login(){
        return "login";
    }
}
