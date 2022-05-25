package com.cars.cardealership.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Make.findAll", query = "select a from Make as a")
})
@Table(name = "MAKE")
@Getter
@Setter
public class Make implements Serializable {

    public Make(){}

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Basic
    private String name;

    @OneToMany(mappedBy = "make")
    private List<Car> cars;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

}
