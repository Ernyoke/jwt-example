package esz.dev.jwtexample.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class JwtUser implements UserDetails {
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    private Integer id;

    private String username;

    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;

    @Getter(onMethod = @__(@JsonIgnore))
    private final Date lastPasswordResetDate;

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
}

