package com.cars.cardealership.rest;

import com.cars.cardealership.entities.Car;
import com.cars.cardealership.entities.Make;
import com.cars.cardealership.persistence.MakesDAO;
import com.cars.cardealership.rest.contracts.CarDto;
import com.cars.cardealership.rest.contracts.MakeDto;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/makes")
public class MakeController {

    @Inject
    @Setter
    @Getter
    private MakesDAO makesDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Make make = makesDAO.findOne(id);
        if (make == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        MakeDto makeDto = new MakeDto();
        makeDto.setMake(make.getName());

        return Response.ok(makeDto).build();
    }
}
