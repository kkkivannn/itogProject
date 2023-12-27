package com.example.individualproject.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Numbers")
public class NumberModel {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Number")
    private long id;
    @Column(name = "NumbNumber")
    @NotNull(message = "Number is required")
    private int numbNumber;
    @Column(name = "CostNumb")
    @NotNull(message = "CostNumb is required")
    private int cost;

    @ManyToOne
    @JoinColumn(name = "TypeId")
    private TypeNumbModel types;

    @OneToOne(mappedBy = "numberModel", cascade = CascadeType.ALL)
    private ReservationModel reservationModel;

    public NumberModel(){}

    public NumberModel(int numbNumber, int cost) {
        this.numbNumber = numbNumber;
        this.cost = cost;
    }

    public ReservationModel getReservationModel() {
        return reservationModel;
    }

    public void setReservationModel(ReservationModel reservationModel) {
        this.reservationModel = reservationModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumbNumber() {
        return numbNumber;
    }

    public void setNumbNumber(int numbNumber) {
        this.numbNumber = numbNumber;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public TypeNumbModel getTypes() {
        return types;
    }

    public void setTypes(TypeNumbModel types) {
        this.types = types;
    }
}
