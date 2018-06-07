package com.inamona.resources;

import com.codahale.metrics.annotation.Timed;
import com.inamona.api.Game;
import com.inamona.api.Hand;
import com.inamona.api.filters.authentication.Secured;
import com.inamona.db.GameDAO;
import com.inamona.db.HandDAO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * @author christopher
 * @since 5/13/18
 */
@Secured
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
    public Response getAllGames(@Context UriInfo uriInfo) {
        final List<Game> games = this.gameDAO.findAll();
        games.forEach(game -> game.setLinks(uriInfo));
        return Response.ok().entity(games).build();
    }

    @GET
    @Path("/{gameId}")
    @Timed
    @UnitOfWork
    public Response getGameById(@PathParam("gameId") final long gameId, @Context UriInfo uriInfo) {
        final Game game = this.gameDAO.findById(gameId);
        game.setLinks(uriInfo);
        return Response.ok()
            .entity(game)
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
    public Response createGame(@Context final UriInfo uriInfo) {
        final Game game = this.gameDAO.create(new Game());
        final URI gameUri = uriInfo.getBaseUriBuilder()
            .path(GameResource.class)
            .path(String.valueOf(game.getGameId()))
            .build();
        return Response.created(gameUri).build();
    }

    @POST
    @Path("/{gameId}/hands")
    @Timed
    @UnitOfWork
    public Response createHandForGame(@PathParam("gameId") final long gameId, @Context final UriInfo uriInfo) {
        final Game game = this.gameDAO.findById(gameId);
        final Hand hand = game.addHand();
        hand.setGame(game);
        this.handDAO.create(hand);
        return Response.created(hand.selfUri(uriInfo)).build();
    }
}