package com.inamona.api.filters.authentication;

import com.inamona.api.Token;
import com.inamona.api.User;
import com.inamona.db.TokenDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
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
     * The TokenDAO.
     */
    private final TokenDAO tokenDAO;

    @Override
    @UnitOfWork
    public Optional<User> authenticate(final String credentials) throws AuthenticationException {
        final Token token = this.tokenDAO.findByToken(credentials);
        final User user = token.getUser();
        if (nonNull(user)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
