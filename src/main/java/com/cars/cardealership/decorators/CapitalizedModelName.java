package com.cars.cardealership.decorators;

import com.cars.cardealership.services.ModelGenerator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class CapitalizedModelName implements ModelGenerator {

    @Inject @Delegate @Any
    ModelGenerator modelGenerator;

    public String generateModel(String make) {
        String model = modelGenerator.generateModel(make);
        return model.toUpperCase();
    }
}
