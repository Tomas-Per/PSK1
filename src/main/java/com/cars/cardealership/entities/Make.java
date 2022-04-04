package com.cars.cardealership.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Make.findAll", query = "select a from Make as a")
})
@Table(name = "MAKE")
@Getter
@Setter
public class Make {

    public Make(){}

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Basic
    private String name;

    @OneToMany(mappedBy = "make")
    private List<Car> cars;

}
