package com.cars.cardealership.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Car.findAll", query = "select a from Car as a")
})
@Table(name = "CAR")
@Getter
@Setter
public class Car implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column (name = "carModel")
    private String model;


    @ManyToOne
    private Make make;


    @ManyToMany(mappedBy = "cars")
    private List<Shop> shops;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

}
