package cn.codenest.springsecurityoauthgithub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/6 18:47
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
public class SimpleController {

    @GetMapping("/hello")
    public String hello(Principal principal) {
        return "Hello " + principal.getName();
    }

}
