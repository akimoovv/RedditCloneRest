package com.springboot.reditclone.demo.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permissions.WRITE, Permissions.READ)),
    USER(Set.of(Permissions.READ));

    private Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }


    public Set<SimpleGrantedAuthority> getAuthorities() {

        return this.getPermissions().stream().map(perm -> new SimpleGrantedAuthority(perm.getPermission()))
                .collect(Collectors.toSet());


    }
}
