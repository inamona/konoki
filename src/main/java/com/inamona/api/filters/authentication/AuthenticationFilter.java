package com.inamona.api.filters.authentication;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

import static java.util.Objects.nonNull;

/**
 * @author christopher
 * @since 5/29/18
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        final SecurityContext requestSecurityContext = requestContext.getSecurityContext();

        final Response unauthorizedResponse = Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity("You aint authorized, bish...").build();
        if (!this.isTokenBasedAuthentication(authorizationHeader)) {
            requestContext.abortWith(unauthorizedResponse);
            return;
        }

        final String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        try {
            this.validateToken(token);
        } catch (final InvalidTokenException e) {
            requestContext.abortWith(unauthorizedResponse);
        }
    }

    private boolean isTokenBasedAuthentication(final String authorizationHeader) {
        return nonNull(authorizationHeader) && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void validateToken(final String token) throws InvalidTokenException {
        if (!token.equals("foo")) {
            throw new InvalidTokenException();
        }
    }
}
