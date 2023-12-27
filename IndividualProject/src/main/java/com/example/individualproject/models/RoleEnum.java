package com.example.individualproject.models;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority{
    USER, ADMIN, SOTRUDNIK;
    @Override
    public String getAuthority(){
        return name();
    }
}
