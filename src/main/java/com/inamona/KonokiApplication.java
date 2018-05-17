package com.inamona;

import com.inamona.api.Game;
import com.inamona.api.Hand;
import com.inamona.db.GameDAO;
import com.inamona.db.HandDAO;
import com.inamona.health.BasicHealthCheck;
import com.inamona.resources.GameResource;
import com.inamona.resources.HandResource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class KonokiApplication extends Application<KonokiConfiguration> {

    private final HibernateBundle<KonokiConfiguration> hibernateBundle = new HibernateBundle<KonokiConfiguration>(Game.class, Hand.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(KonokiConfiguration konokiConfiguration) {
            return konokiConfiguration.getDatabase();
        }
    };

    public static void main(final String[] args) throws Exception {
        new KonokiApplication().run(args);
    }

    @Override
    public String getName() {
        return "konoki";
    }

    @Override
    public void initialize(final Bootstrap<KonokiConfiguration> bootstrap) {
        bootstrap.addBundle(this.hibernateBundle);
    }

    @Override
    public void run(final KonokiConfiguration configuration, final Environment environment) {
        final GameDAO gameDAO = new GameDAO(this.hibernateBundle.getSessionFactory());
        final HandDAO handDAO = new HandDAO(this.hibernateBundle.getSessionFactory());

        environment.healthChecks().register("basic", new BasicHealthCheck());
        environment.jersey().register(new HandResource(handDAO));
        environment.jersey().register(new GameResource(gameDAO, handDAO));
    }

}
