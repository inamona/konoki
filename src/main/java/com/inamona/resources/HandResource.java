package com.inamona.resources;

import com.codahale.metrics.annotation.Timed;
import com.inamona.db.HandDAO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author christopher
 * @since 5/15/18
 */
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
@Path("hands")
public class HandResource {
    /**
     * The {@link HandDAO}.
     */
    private final HandDAO handDAO;

    @GET
    @Timed
    @UnitOfWork
    public Response getAllHands() {
        return Response.ok().entity(this.handDAO.findAll()).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("{handId}")
    public Response getHand(@PathParam("handId") final long handId) {
        return Response.ok().entity(this.handDAO.findById(handId)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/{gameId}/hands")
    public Response getAllHandsForGame(@PathParam("gameId") final long gameId) {
        return Response.ok().entity(this.handDAO.findAll(gameId)).build();
    }
}
