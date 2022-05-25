package com.cars.cardealership.services;

import javax.enterprise.inject.Specializes;
import java.util.List;
import java.util.Random;

@Specializes
public class CarModelGeneratorSlow extends CarModelGenerator {

    public String generateModel(String make) {
        try {
            Thread.sleep(2000); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        List<String> models = ModelNames.makeModels.get(make);
        Random rand = new Random();
        return models.get(rand.nextInt(models.size()));
    }
}
