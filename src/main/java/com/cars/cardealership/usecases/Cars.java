package com.cars.cardealership.usecases;

import com.cars.cardealership.entities.Car;
import com.cars.cardealership.entities.Make;
import com.cars.cardealership.persistence.CarsDAO;
import com.cars.cardealership.persistence.MakesDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Model
public class Cars {

    @Inject
    private CarsDAO carsDAO;

    @Getter
    @Setter
    private Car carToCreate = new Car();

    @PostConstruct
    public void init(){

        carToCreate.setMake(new Make());

    }

    @Transactional
    public void createCars(){

        this.carsDAO.persist(carToCreate);
    }
}
