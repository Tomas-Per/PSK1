package com.cars.cardealership.usecases;

import com.cars.cardealership.mybatis.dao.MakeMapper;
import com.cars.cardealership.mybatis.model.Make;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class MakesMyBatis {

    @Inject
    private MakeMapper makeMapper;

    @Getter
    private List<Make> allMakes;

    @Getter @Setter
    private Make makeToCreate = new Make();

    @PostConstruct
    public void init() {
        this.loadAllMakes();
    }

    private void loadAllMakes() {
        this.allMakes = makeMapper.selectAll();
    }

    @Transactional
    public String createMake() {
        makeMapper.insert(makeToCreate);
        return "/myBatis/makes?faces-redirect=true";
    }
}
