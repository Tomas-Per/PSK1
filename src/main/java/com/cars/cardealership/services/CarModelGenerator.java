package com.cars.cardealership.services;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class CarModelGenerator implements ModelGenerator {

    public String generateModel(String make) {
        List<String> models = ModelNames.makeModels.get(make);
        Random rand = new Random();
        return models.get(rand.nextInt(models.size()));

    }
}
