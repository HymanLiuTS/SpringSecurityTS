package cn.codenest.springsecurityts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import java.util.Map;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/31 16:22
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class SessionConfig {

    @Autowired
    RedisTemplate redisTemplate;


    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry(new RedisOperationsSessionRepository(redisTemplate));
    }

    //todo 如果要使用spring security的会话管理，必须配置会话事件监听器，否则当用户注销登录时，有效会话数目不会减少，如果达到了最大会话数，用户还是不能创建新的会话
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

}
