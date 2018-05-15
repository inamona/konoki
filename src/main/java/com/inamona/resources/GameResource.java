package com.inamona.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import com.inamona.api.Game;
import com.inamona.db.GameDAO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author christopher
 * @since 5/13/18
 */
@Path("games")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class GameResource {
    /**
     * The GameDAO.
     */
    private final GameDAO gameDAO;

    @GET
    @Timed
    @UnitOfWork
    public Response getAllGames() {
        return Response.ok().entity(this.gameDAO.findAll()).build();
    }

    @GET
    @Path("/{gameId}")
    @Timed
    @UnitOfWork
    public Response getGameById(@PathParam("gameId") final long gameId) {
        return Response.ok().entity(this.gameDAO.findById(gameId)).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public void createGame() throws URISyntaxException {
        this.gameDAO.create(new Game());
    }
}