package com.cars.cardealership.persistence;

import com.cars.cardealership.entities.Make;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class MakesDAO {

    @Inject
    private EntityManager em;

    public List<Make> loadAll() {
        return em.createNamedQuery("Make.findAll", Make.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Make make){
        this.em.persist(make);
    }

    public Make findOne(Integer id) {
        return em.find(Make.class, id);
    }
}
