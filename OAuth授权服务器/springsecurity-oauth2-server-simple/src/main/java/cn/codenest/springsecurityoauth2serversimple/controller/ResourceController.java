package cn.codenest.springsecurityoauth2serversimple.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * @author ：Hyman
 * @date ：Created in 2021/1/8 12:28
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@Profile("simple")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @RequestMapping("/me")
    public Principal me(Principal principal) {
        logger.debug(principal.toString());
        return principal;
    }
}
