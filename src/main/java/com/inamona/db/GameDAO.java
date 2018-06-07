package com.inamona.db;

import com.inamona.api.Game;
import com.inamona.api.Hand;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author christopher
 * @since 5/14/18
 */
public class GameDAO extends AbstractDAO<Game> {
    public GameDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Game findById(final long id) {
        return this.get(id);
    }

    public Game create(final Game game) {
        return this.persist(game);
    }

    public Hand addHand(final Game game) {
        final Hand hand = game.addHand();
        this.persist(game);
        return hand;
    }

    public List<Game> findAll() {
        final EntityManager entityManager = this.currentSession().getEntityManagerFactory().createEntityManager();
        return entityManager.createQuery("select g from Game g", Game.class).getResultList();
    }
}
