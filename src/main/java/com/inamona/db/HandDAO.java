package com.inamona.db;

import com.inamona.api.Hand;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    public Hand create(final Hand hand) {
        return this.persist(hand);
    }

    public List<Hand> findAll() {
        return this.list(this.currentSession().createQuery("from Hand hand", Hand.class));
    }

    public List<Hand> findAll(final long gameId) {
        final EntityManager entityManager = this.currentSession().getEntityManagerFactory().createEntityManager();
        final TypedQuery<Hand> handsByGameIdQuery = entityManager.createQuery(
            "select h from Hand h where h.game_id = :gameId",
            Hand.class
        ).setParameter("gameId", gameId);

        return handsByGameIdQuery.getResultList();
    }
}
