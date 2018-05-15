package com.inamona.db;

import com.inamona.api.Hand;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * @author christopher
 * @since 5/14/18
 */
public class HandDAO extends AbstractDAO<Hand> {
    public HandDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
