package com.example.individualproject.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "NumberType")
public class TypeNumbModel {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Type")
    private long id;
    @Column(name = "NameType")
    @NotBlank(message = "Name is required")
    private String name;

    @OneToMany(mappedBy = "types", cascade = CascadeType.ALL) // mappedBy указывает на поле в классе UserModel, которое управляет этой связью
    private List<NumberModel> numbers;

    public TypeNumbModel(){}

    public List<NumberModel> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<NumberModel> numbers) {
        this.numbers = numbers;
    }

    public TypeNumbModel(String name) {
        this.name = name;
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
}
