package cn.codenest.springsecurityts.exception;

import javax.security.sasl.AuthenticationException;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/30 9:28
 * @description：
 * @modified By：
 * @version: $
 */
public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(){
        super("图形验证码校验失败");
    }
}
