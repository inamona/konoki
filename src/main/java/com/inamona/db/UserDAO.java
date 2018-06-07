package com.inamona.db;

import com.inamona.api.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author christopher
 * @since 6/6/18
 */
public class UserDAO extends AbstractDAO<User> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public UserDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll() {
        return this.list(this.currentSession().createQuery("from User user", User.class));
    }

    public User create(final String email, final String salt, final String hashedPassword) {
        final User user = new User(email, salt, hashedPassword);
        return this.persist(user);
    }
}
