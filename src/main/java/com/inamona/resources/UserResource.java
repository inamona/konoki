package com.inamona.resources;

import com.codahale.metrics.annotation.Timed;
import com.inamona.api.User;
import com.inamona.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * @author christopher
 * @since 6/6/18
 */
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
@Path("users")
public class UserResource {
    /**
     * The {@link UserDAO}.
     */
    private final UserDAO userDAO;

    @GET
    @Timed
    @UnitOfWork
    public Response getAllUsers() {
        return Response.ok().entity(this.userDAO.findAll()).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public Response createUser(
        @FormParam("email") final String email,
        @FormParam("password") final String password,
        @Context final UriInfo uriInfo
    ) {
        final String salt = BCrypt.gensalt();
        final String hashedPassword = BCrypt.hashpw(password, salt);
        final User user = this.userDAO.create(email, salt, hashedPassword);
        final URI userUri = uriInfo.getBaseUriBuilder()
            .path(UserResource.class)
            .path(String.valueOf(user.getUserId()))
            .build();
        return Response.created(userUri).build();
    }
}
