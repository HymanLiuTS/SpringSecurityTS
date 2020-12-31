package cn.codenest.springsecurityts.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：Hyman
 * @date ：Created in 2020/12/29 12:01
 * @description：
 * @modified By：
 * @version: $
 */
@Data
@NoArgsConstructor
public class User implements UserDetails {

    private static final long serialVersionUID = 2020L;
    private String password;
    private String username;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled = true;
    private Long id;
    String roles;

    public User(Long id, String username, String password, Boolean endabled, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = endabled;
        this.roles = roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //todo 使用会话管理进行会话并发控制时，必须要重载equal和hashcode，因为spring security采用hashmap作为会话信息表，键为User对象，如果不重载这两个方法，会导致对相同用户hash得到不同值
    @Override
    public boolean equals(Object rhs) {
        return rhs instanceof org.springframework.security.core.userdetails.User ? this.username.equals(((org.springframework.security.core.userdetails.User)rhs).getUsername()) : false;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    public void setRoles(String roles) {
        this.roles = roles;
        if (StringUtils.isEmpty(roles) == false) {
            this.authorities = new HashSet<>();
            List<GrantedAuthority> tmp = AuthorityUtils.commaSeparatedStringToAuthorityList(this.getRoles());
            tmp.stream().forEach(x -> this.authorities.add(x));
        }
    }
}


