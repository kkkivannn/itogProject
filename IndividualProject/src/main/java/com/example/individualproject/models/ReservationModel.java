package com.example.individualproject.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Reservation")
public class ReservationModel {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Reservation")
    private long id;
    @Column(name = "start_liv")
    @NotNull(message = "Number is required")
    private LocalDate start;
    @Column(name = "end_liv")
    @NotNull(message = "CostNumb is required")
    private LocalDate end;

    @OneToOne
    @JoinColumn(name = "Number_Id")
    private NumberModel numberModel;

    @OneToOne
    @JoinColumn(name = "User_Id")
    private UserModel userModel;

    public ReservationModel(){}

    public ReservationModel(LocalDate start, LocalDate end, NumberModel numberModel, UserModel userModel) {
        this.start = start;
        this.end = end;
        this.numberModel = numberModel;
        this.userModel = userModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public NumberModel getNumberModel() {
        return numberModel;
    }

    public void setNumberModel(NumberModel numberModel) {
        this.numberModel = numberModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
