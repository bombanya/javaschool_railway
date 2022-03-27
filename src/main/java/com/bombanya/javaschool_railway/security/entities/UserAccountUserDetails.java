package com.bombanya.javaschool_railway.security.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Builder
public class UserAccountUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public static UserAccountUserDetails fromUserAccount(UserAccount account){
        return UserAccountUserDetails.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities(account.getRoles()
                        .stream()
                        .map(userRoleEntity -> new SimpleGrantedAuthority("ROLE_" + userRoleEntity
                                .getName()
                                .name()))
                        .collect(Collectors.toList()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}
