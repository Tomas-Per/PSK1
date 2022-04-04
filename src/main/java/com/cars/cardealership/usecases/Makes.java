package com.cars.cardealership.usecases;

import com.cars.cardealership.entities.Make;
import com.cars.cardealership.persistence.MakesDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Makes {

    @Inject
    private MakesDAO makesDAO;

    @Getter
    @Setter
    private com.cars.cardealership.entities.Make makeToCreate = new Make();

    @Getter
    private List<Make> allMakes;

    @PostConstruct
    public void init(){

        loadAllMakes();
    }

    @Transactional
    public void createMakes(){
        this.makesDAO.persist(makeToCreate);
    }

    private void loadAllMakes(){

        this.allMakes = makesDAO.loadAll();
    }

}
