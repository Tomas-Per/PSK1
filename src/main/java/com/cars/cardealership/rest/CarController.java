package com.cars.cardealership.rest;

import com.cars.cardealership.entities.Car;
import com.cars.cardealership.entities.Make;
import com.cars.cardealership.entities.Shop;
import com.cars.cardealership.persistence.CarsDAO;
import com.cars.cardealership.persistence.MakesDAO;
import com.cars.cardealership.persistence.ShopsDAO;
import com.cars.cardealership.rest.contracts.CarDto;
import com.cars.cardealership.rest.contracts.ShopDto;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("/cars")
public class CarController {

    @Inject
    @Setter
    @Getter
    private CarsDAO carsDAO;

    @Inject
    @Setter
    @Getter
    private MakesDAO makesDAO;

    @Inject
    @Setter
    @Getter
    private ShopsDAO shopsDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Long id) {
        Car car = carsDAO.findOne(id);
        if (car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        CarDto carDto = new CarDto();
        carDto.setModel(car.getModel());
        carDto.setMake(car.getMake().getName());

        List<ShopDto> shopDtos = new ArrayList<>();

        for(Shop shop : car.getShops()) {
            ShopDto shopDto = new ShopDto();
            shopDto.setId(shop.getId());
            shopDto.setName(shop.getName());

            shopDtos.add(shopDto);
        }

        carDto.setShops(shopDtos);

        return Response.ok(carDto).build();
    }

    @POST
    @Path("/post/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCar(CarDto carDto) {
        Car car = new Car();
        car.setModel(carDto.getModel());
        Make make = makesDAO.findOneByName(carDto.getMake());
        car.setMake(make);

        for(ShopDto shopDto : carDto.getShops()) {
            Shop shop = shopsDAO.findOne(shopDto.getId());
            List<Car> cars = shop.getCars();
            cars.add(car);
            shop.setCars(cars);
            shopsDAO.persist(shop);
        }

        return Response.status(201).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") final Long carId, CarDto carDto) {
        try {
            Car existingCar = carsDAO.findOne(carId);
            if (existingCar == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingCar.setModel(carDto.getModel());
            Make make = makesDAO.findOneByName(carDto.getMake());
            existingCar.setMake(make);

            for(ShopDto shopDto : carDto.getShops()) {
                Shop shop = shopsDAO.findOne(shopDto.getId());

                List<Car> cars = shop.getCars();

                if(cars.contains(existingCar)) {
                    continue;
                }
                cars.add(existingCar);
                shop.setCars(cars);
                shopsDAO.update(shop);
            }

            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
