package cn.codenest.springcloudts.mapper;

import cn.codenest.springcloudts.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 13:49
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class UserMapper {

    private HashMap<String, User> users = null;

    public UserMapper() {
        users = new HashMap<>();
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("123456");
        //由于WebSecurityConfig.config中使用了hasRole,这里要加ROLE_前缀
        user.setRoles("ROLE_USER");//由于
        users.put("user", user);
        User admin = new User();
        admin.setId(2L);
        admin.setUsername("admin");
        admin.setPassword("123456");
        //由于WebSecurityConfig.config中使用了hasAuthority,这里不要加ROLE_前缀
        admin.setRoles("ADMIN");
        users.put("admin", admin);
    }

    public User findByUserName(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }

}
