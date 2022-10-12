package ru.sinforge.mywebapplication.Entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    Administrator,
    DefaultUser,
    Moderator;

    @Override
    public String getAuthority() {
        return name();  
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
