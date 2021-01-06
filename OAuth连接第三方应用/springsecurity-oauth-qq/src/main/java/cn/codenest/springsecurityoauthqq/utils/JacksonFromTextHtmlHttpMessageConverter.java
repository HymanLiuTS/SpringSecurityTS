package cn.codenest.springsecurityoauthqq.utils;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/6 19:18
 * @description：
 * @modified By：
 * @version: $
 */
public class JacksonFromTextHtmlHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public JacksonFromTextHtmlHttpMessageConverter() {
        List mediaTypes = new ArrayList();
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);
    }
}
