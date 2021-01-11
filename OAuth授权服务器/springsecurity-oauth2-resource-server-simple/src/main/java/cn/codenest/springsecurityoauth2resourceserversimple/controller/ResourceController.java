package cn.codenest.springsecurityoauth2resourceserversimple.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/11 19:15
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @GetMapping("/resource")
    public String resource() {
        logger.info("in resource");
        return "resource";
    }
}
