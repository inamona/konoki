package com.inamona.db;

import com.inamona.api.Token;
import com.inamona.api.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author christopher
 * @since 6/6/18
 */
public class TokenDAO extends AbstractDAO<Token> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public TokenDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Token create(final User user) {
        final Random random = new SecureRandom();
        final String token = new BigInteger(130, random).toString(32);

        return this.persist(new Token(user, token));
    }

    public Token findByToken(final String token) {
        return this.currentSession().byNaturalId(Token.class).using("token", token).load();
    }
}
