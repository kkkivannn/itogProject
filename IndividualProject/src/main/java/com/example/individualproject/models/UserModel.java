package com.example.individualproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
public class UserModel {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_User")
    private long id;
    @Column(name = "Email")
    @NotBlank(message = "Email is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Name is required")
    private String NameUs;

    @NotBlank(message = "Surname is required")
    private String UsernameUs;

    private boolean active;

    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> roles;

    @OneToOne(mappedBy = "numberModel", cascade = CascadeType.ALL)
    private ReservationModel reservationModel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewModel> reviews;

    public UserModel() {}

    public UserModel(String username, String password, String NameUs, String UsernameUs, Boolean active, Set<RoleEnum> roles) {
        this.username = username;
        this.password = password;
        this.NameUs = NameUs;
        this.UsernameUs = UsernameUs;
        this.active = active;
        this.roles = roles;
    }

    public ReservationModel getReservationModel() {
        return reservationModel;
    }

    public void setReservationModel(ReservationModel reservationModel) {
        this.reservationModel = reservationModel;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String _username) {
        this.username = _username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNameUs() {
        return NameUs;
    }

    public void setNameUs(String nameUs) {
        NameUs = nameUs;
    }

    public String getUsernameUs() {
        return UsernameUs;
    }

    public void setUsernameUs(String usernameUs) {
        UsernameUs = usernameUs;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
    }
}
