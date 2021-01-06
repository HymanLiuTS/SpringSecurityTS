package cn.codenest.springsecurityoauthqq.utils;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author ：Hyman
 * @date ：Created in 2021/1/6 19:21
 * @description：
 * @modified By：
 * @version: $
 */
public class TextHtmlHttpMessageConvert extends AbstractHttpMessageConverter {

    public TextHtmlHttpMessageConvert() {
        super(Charset.forName("UTF-8"), new MediaType[]{MediaType.TEXT_HTML});
    }

    @Override
    protected boolean supports(Class clazz) {
        return String.class == clazz;
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Charset charset = this.getContentTypeCharset(inputMessage.getHeaders().getContentType());
        return StreamUtils.copyToString(inputMessage.getBody(),charset);
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        return contentType != null && contentType.getCharset() != null ?
                contentType.getCharset() : this.getDefaultCharset();
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        
    }
}
