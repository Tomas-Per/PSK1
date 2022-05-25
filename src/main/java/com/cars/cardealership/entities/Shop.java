package com.cars.cardealership.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "SHOP")
@Getter
@Setter
public class Shop implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Basic
    private String name;


//    @ManyToMany(mappedBy = "shops")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="CAR_SHOP",
            joinColumns=@JoinColumn(name="SHOPS_ID", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="CARS_ID", referencedColumnName="id"))
    private List<Car> cars;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

}
