package com.cars.cardealership.rest;

import com.cars.cardealership.entities.Make;
import com.cars.cardealership.persistence.MakesDAO;
import com.cars.cardealership.rest.contracts.MakeDto;
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
@Path("/makes")
public class MakeController {

    @Inject
    @Setter
    @Getter
    private MakesDAO makesDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Long id) {
        Make make = makesDAO.findOne(id);
        if (make == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        MakeDto makeDto = new MakeDto();
        makeDto.setMake(make.getName());

        return Response.ok(makeDto).build();
    }

    @POST
    @Path("/post/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createMake(MakeDto makeDto) {
        Make make = new Make();
        make.setName(makeDto.getMake());

        makesDAO.persist(make);

        return Response.status(201).entity(make).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") final Long makeId, MakeDto makeDto) {
        try {
            Make existingMake = makesDAO.findOne(makeId);
            if (existingMake == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingMake.setName(makeDto.getMake());
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
