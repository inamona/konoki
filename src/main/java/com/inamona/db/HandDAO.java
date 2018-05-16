package com.inamona.db;

import com.inamona.api.Hand;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author christopher
 * @since 5/14/18
 */
public class HandDAO extends AbstractDAO<Hand> {
    public HandDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Hand findById(final long id) {
        return this.get(id);
    }

    public long create(final Hand hand) {
        return this.persist(hand).getHandId();
    }

    public List<Hand> findAll() {
        return this.list(this.namedQuery("com.inamona.api.Hand.findAll"));
    }
}
