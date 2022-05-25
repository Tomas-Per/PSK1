package com.cars.cardealership.rest;

import com.cars.cardealership.entities.Car;
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
@Path("/shops")
public class ShopController {

    @Inject
    @Setter
    @Getter
    private ShopsDAO shopsDAO;

    @Inject
    @Setter
    @Getter
    private CarsDAO carsDAO;

    @Inject
    @Setter
    @Getter
    private MakesDAO makesDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Long id) {
        Shop shop = shopsDAO.findOne(id);
        if (shop == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ShopDto shopDto = new ShopDto();
        shopDto.setName(shop.getName());

        List<CarDto> carDtos = new ArrayList<>();

        for(Car car : shop.getCars()) {
            CarDto carDto = new CarDto();
            carDto.setId(car.getId());
            carDto.setModel(car.getModel());
            carDto.setMake(car.getMake().getName());

            carDtos.add(carDto);
        }

        shopDto.setCars(carDtos);

        return Response.ok(shopDto).build();
    }

    @POST
    @Path("/post/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createShop(ShopDto shopDto) {
        Shop shop = new Shop();
        shop.setName(shopDto.getName());

        List<Car> cars = new ArrayList<>();

        for(CarDto carDto : shopDto.getCars()) {
            Car car = carsDAO.findOne(carDto.getId());
            cars.add(car);
        }
        shop.setCars(cars);
        shopsDAO.persist(shop);

        return Response.status(201).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") final Long shopId, ShopDto shopDto) {
        try {
            Shop existingShop = shopsDAO.findOne(shopId);
            if (existingShop == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingShop.setName(shopDto.getName());

            List<Car> cars = new ArrayList<>();

            for(CarDto carDto : shopDto.getCars()) {
                Car car = carsDAO.findOne(carDto.getId());
                cars.add(car);
            }
            existingShop.setCars(cars);
            shopsDAO.update(existingShop);;

            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
