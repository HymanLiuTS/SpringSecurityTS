package cn.codenest.springcloudts.controller;

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
@RequestMapping("/admin/api")
public class AdminController {

    @ResponseBody
    @GetMapping("/role")
    public String hello(){
        return "admin";
    }

}
