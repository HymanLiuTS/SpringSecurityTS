package cn.codenest.springsecurityoauth2serversimple.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/8 12:25
 * @description：
 * @modified By：
 * @version: $
 */
@Profile("simple")
@RestController
public class MainController {

    @GetMapping("/")
    public String email() {
        return "这是主页";
    }

    @GetMapping("/admin")
    public String admin() {
        return "这是admin页";
    }

    @GetMapping("/user")
    public String user() {
        return "这是user页";
    }
}
