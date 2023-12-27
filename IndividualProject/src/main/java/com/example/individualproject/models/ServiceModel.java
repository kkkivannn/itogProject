package com.example.individualproject.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Service")
public class ServiceModel {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Service")
    private long id;
    @Column(name = "name_service")
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "cost_serv")
    @NotNull(message = "Cost is required")
    private int cost;


    public ServiceModel(){}

    public ServiceModel(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
