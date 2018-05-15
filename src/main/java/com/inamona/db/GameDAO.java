package com.inamona.db;

import com.inamona.api.Game;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author christopher
 * @since 5/14/18
 */
public class GameDAO extends AbstractDAO<Game> {
    public GameDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Game findById(long id) {
        return this.get(id);
    }

    public long create(final Game game) {
        return this.persist(game).getGameId();
    }

    public List<Game> findAll() {
        return this.list(this.namedQuery("com.inamona.api.Game.findAll"));
    }
}