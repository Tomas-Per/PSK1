package com.cars.cardealership.rest.contracts;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarDto {

    private Long id;

    private String model;

    private String make;

    private List<ShopDto> shops;
}
