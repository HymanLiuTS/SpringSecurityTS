package cn.codenest.springsecurityoauthqq.registrations.qq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>springsecurityts</h3>
 * <p></p>
 *
 * @author : Hyman
 * @date : 2021-01-06 19:03
 **/
@Data
public class QQUser implements OAuth2User {

    private List<GrantedAuthority> authoritys = AuthorityUtils.createAuthorityList("ROLE_USER");

    private Map<String, Object> attributes;

    private String nickname;

    @JsonProperty("figureurl")
    private String figureUrl30;

    @JsonProperty("figureurl_1")
    private String figureUrl50;

    @JsonProperty("figureurl_2")
    private String figureUrl100;

    @JsonProperty("figureurl_qq_1")
    private String qqFigureUrl40;

    @JsonProperty("figureurl_qq_2")
    private String qqFigureUrl100;

    private String gender;

    //QQ需要Openid才能获取授权
    private String openid;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authoritys;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<>();
            attributes.put("nickname", this.getNickname());
            attributes.put("figureUrl30", this.getFigureUrl30());
            attributes.put("figureUrl50", this.getFigureUrl50());
            attributes.put("figureUrl100", this.getFigureUrl100());
            attributes.put("qqFigureUrl40", this.getQqFigureUrl40());
            attributes.put("qqFigureUrl100", this.getQqFigureUrl100());
            attributes.put("gender", this.getGender());
        }
        return attributes;
    }

    @Override
    public String getName() {
        return this.nickname;
    }
}
