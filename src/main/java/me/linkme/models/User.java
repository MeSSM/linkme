package me.linkme.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RedisHash("User")
@Getter @Setter
public class User implements UserDetails {

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<Roles> Roles;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Roles.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
    }

    public String getUsername() {
        return email;
    }

    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    public boolean isEnabled() {
        return true;
    }
}
