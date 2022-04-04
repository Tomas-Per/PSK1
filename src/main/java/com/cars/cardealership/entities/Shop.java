package com.cars.cardealership.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SHOP")
@Getter
@Setter
public class Shop {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Basic
    private String name;


    @ManyToMany(mappedBy = "shops")
    private List<Car> cars;

}
