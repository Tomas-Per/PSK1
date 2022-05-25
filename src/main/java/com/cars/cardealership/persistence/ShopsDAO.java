package com.cars.cardealership.persistence;

import com.cars.cardealership.entities.Shop;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class ShopsDAO {

    @Inject
    private EntityManager em;

    public void persist(Shop shop){
        this.em.persist(shop);
    }

    public Shop findOne(Long id) {
        return em.find(Shop.class, id);
    }

    public Shop update(Shop shop) {
        return em.merge(shop);
    }

    public Shop findOneByName(String name) {
        return em.find(Shop.class, name);
    }
}
