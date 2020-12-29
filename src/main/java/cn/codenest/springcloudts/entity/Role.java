package cn.codenest.springcloudts.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 12:08
 * @description：
 * @modified By：
 * @version: $
 */
public class Role implements GrantedAuthority {

    String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
