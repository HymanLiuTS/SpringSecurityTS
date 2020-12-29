package cn.codenest.springsecurityts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 11:20
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/app/api")
public class AppController {
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
