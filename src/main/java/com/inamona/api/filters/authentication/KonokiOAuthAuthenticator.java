package com.inamona.api.filters.authentication;

import com.inamona.api.User;
import com.inamona.db.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * @author christopher
 * @since 6/7/18
 */
@AllArgsConstructor
public class KonokiOAuthAuthenticator implements Authenticator<String, User> {
    /**
     * The HS256 key.
     */
    private final String hs256key;
    /**
     * The UserDAO.
     */
    private final UserDAO userDAO;

    @Override
    @UnitOfWork
    public Optional<User> authenticate(final String credentials) throws AuthenticationException {
        final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(this.hs256key).parseClaimsJws(credentials);
        final String email = claimsJws.getBody().getSubject();
        final User user = this.userDAO.findByEmail(email);
        if (nonNull(user)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
