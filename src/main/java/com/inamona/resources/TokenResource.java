package com.inamona.resources;

import com.inamona.api.User;
import com.inamona.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * @author christopher
 * @since 6/6/18
 */
@Path("token")
@AllArgsConstructor
public class TokenResource {
    /**
     * The UserDAO.
     */
    private final UserDAO userDAO;

    /**
     * The HS256 key.
     */
    private final String hs256key;

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
            final String jwt = this.issueToken(user);
            return Response.ok(jwt).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    private String issueToken(final User user) {
        final Date expirationDate = Date.from(Instant.now().plusSeconds(3600L));
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setExpiration(expirationDate)
            .compressWith(CompressionCodecs.DEFLATE)
            .signWith(SignatureAlgorithm.HS256, this.hs256key)
            .compact();
    }
}
