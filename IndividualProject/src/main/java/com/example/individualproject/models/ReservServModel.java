package com.example.individualproject.models;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reserv_serv")
public class ReservServModel {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_reserv_serv")
    private long id;

    @ManyToOne
    @JoinColumn(name = "User_Id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "Serv_Id", nullable = false)
    private ServiceModel service;

    @Column(name = "date_serv", nullable = false)
    private LocalDate dateServ;

    public ReservServModel() {}

    public ReservServModel(UserModel user, ServiceModel service, LocalDate dateServ) {
        this.user = user;
        this.service = service;
        this.dateServ = dateServ;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ServiceModel getService() {
        return service;
    }

    public void setService(ServiceModel service) {
        this.service = service;
    }

    public LocalDate getDateServ() {
        return dateServ;
    }

    public void setDateServ(LocalDate dateServ) {
        this.dateServ = dateServ;
    }
}
