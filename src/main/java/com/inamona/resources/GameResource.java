package com.inamona.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import com.inamona.api.Game;
import com.inamona.api.Hand;
import com.inamona.api.representations.GameRepresentation;
import com.inamona.db.GameDAO;
import com.inamona.db.HandDAO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
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

    /**
     * The HandDAO.
     */
    private final HandDAO handDAO;

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
        final Game game = this.gameDAO.findById(gameId);
        return Response.ok()
            .entity(new GameRepresentation(game))
            .build();
    }

    @GET
    @Path("/{gameId}/hands")
    @Timed
    @UnitOfWork
    public Response getAllHandsForGame(@PathParam("gameId") final long gameId) {
        return Response.ok().entity(this.gameDAO.findById(gameId).getHands()).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public void createGame() {
        this.gameDAO.create(new Game());
    }

    @POST
    @Path("/{gameId}/hands")
    @Timed
    @UnitOfWork
    public void createHandForGame(@PathParam("gameId") final long gameId) {
        final Game game = this.gameDAO.findById(gameId);
        final Hand hand = this.handDAO.create(game);
        this.gameDAO.addHand(game, hand);
    }
}