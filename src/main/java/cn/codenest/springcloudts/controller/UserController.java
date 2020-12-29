package cn.codenest.springcloudts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 9:28
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
public class UserController {

    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

}
