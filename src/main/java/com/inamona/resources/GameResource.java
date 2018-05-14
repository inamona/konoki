package com.inamona.resources;

import com.google.common.collect.Lists;
import com.inamona.api.Game;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author christopher
 * @since 5/13/18
 */
@Path("games")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

    private final List<Game> allGames = Lists.newArrayList();

    @GET
    public Response getAllGames() {
        return Response.ok().entity(this.allGames).build();
    }

    @GET
    @Path("/{gameId}")
    public Response getGameById(@PathParam("gameId") final int gameId) {
        return Response.ok().entity(this.findGameById(gameId)).build();
    }

    /**
     * Find a {@link Game} by its numeric ID.
     * @param gameId the ID of the {@link Game}.
     * @return the {@link Game} with the given numeric ID.
     */
    private Game findGameById(final int gameId) {
        return this.allGames.parallelStream().filter(game -> gameId == game.getGameId())
            .findFirst()
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}