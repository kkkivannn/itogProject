package com.example.individualproject.models;

public class UserWithRolesDTO {
    private UserModel user;
    private RoleEnum role;

    public UserWithRolesDTO() {
    }

    public UserWithRolesDTO(UserModel user, RoleEnum role) {
        this.user = user;
        this.role = role;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
