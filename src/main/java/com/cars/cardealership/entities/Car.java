package com.cars.cardealership.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Car.findAll", query = "select a from Car as a")
})
@Table(name = "CAR")
@Getter
@Setter
public class Car {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column (name = "carModel")
    private String model;


    @ManyToOne
    private Make make;


    @ManyToMany
    private List<Shop> shops;

}
