package com.cars.cardealership.rest.contracts;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

 @Getter
 @Setter
public class ShopDto {

    private Long id;

    private String name;

    private List<CarDto> cars;
}
