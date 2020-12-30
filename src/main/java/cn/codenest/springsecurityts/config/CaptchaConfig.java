package cn.codenest.springsecurityts.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/30 9:02
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public Producer captcha() {
        Properties properties = new Properties();
        properties.setProperty("kaprcha.image.width", "150");
        properties.setProperty("kaprcha.image.height", "50");
        properties.setProperty("kaprcha.textproducer.char.string", "0123456789");
        properties.setProperty("kaprcha.textproducer.char.length", "4");
        Config c = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(c);
        return defaultKaptcha;
    }
}
