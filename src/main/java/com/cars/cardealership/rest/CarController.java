package com.cars.cardealership.rest;

import com.cars.cardealership.entities.Car;
import com.cars.cardealership.persistence.CarsDAO;
import com.cars.cardealership.rest.contracts.CarDto;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/cars")
public class CarController {

    @Inject
    @Setter
    @Getter
    private CarsDAO carsDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Car car = carsDAO.findOne(id);
        if (car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        CarDto carDto = new CarDto();
        carDto.setModel(car.getModel());
        carDto.setMake(car.getMake().getName());

        return Response.ok(carDto).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer playerId,
            CarDto carData) {
        try {
            Car existingCar = carsDAO.findOne(playerId);
            if (existingCar == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingCar.setModel(carData.getModel());
//            existingCar.setMake(carData.getMake());
            carsDAO.update(existingCar);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
