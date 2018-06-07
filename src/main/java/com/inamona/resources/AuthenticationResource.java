package com.inamona.resources;

import com.inamona.api.User;
import com.inamona.db.TokenDAO;
import com.inamona.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author christopher
 * @since 6/6/18
 */
@Path("authentication")
@AllArgsConstructor
public class AuthenticationResource {
    /**
     * The UserDAO.
     */
    final UserDAO userDAO;

    /**
     * The TokenDAO.
     */
    final TokenDAO tokenDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @UnitOfWork
    public Response authenticateUser(
        @FormParam("email") final String email,
        @FormParam("password") final String password
    ) {
        final User user = this.userDAO.findByEmail(email);
        if (user.passwordMatches(password)) {
            final String token = this.issueToken(user);
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    /**
     * Issues a token for the given User.
     * @param user the User.
     * @return the API token.
     */
    private String issueToken(final User user) {
        return this.tokenDAO.create(user).getToken();
    }
}
