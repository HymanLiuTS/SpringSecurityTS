package cn.codenest.springsecurityts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 9:28
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/user/api")
public class UserController {

    @ResponseBody
    @GetMapping("/name")
    public String hello(){
        return "Hyman";
    }

    @GetMapping("/login")
    public String login(){
        return "Mylogin.html";
    }

}
