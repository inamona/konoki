package com.inamona;

import com.inamona.api.*;
import com.inamona.api.filters.authentication.KonokiAuthorizer;
import com.inamona.api.filters.authentication.KonokiOAuthAuthenticator;
import com.inamona.db.GameDAO;
import com.inamona.db.HandDAO;
import com.inamona.db.UserDAO;
import com.inamona.health.BasicHealthCheck;
import com.inamona.resources.GameResource;
import com.inamona.resources.HandResource;
import com.inamona.resources.TokenResource;
import com.inamona.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class KonokiApplication extends Application<KonokiConfiguration> {

    private final HibernateBundle<KonokiConfiguration> hibernateBundle = new HibernateBundle<KonokiConfiguration>(
        Game.class, Hand.class, User.class, Token.class, Role.class
    ) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(final KonokiConfiguration konokiConfiguration) {
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
        final UserDAO userDAO = new UserDAO(this.hibernateBundle.getSessionFactory());

        environment.healthChecks().register("basic", new BasicHealthCheck());

        final UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory = new UnitOfWorkAwareProxyFactory(this.hibernateBundle);
        final String hs256key = configuration.getHs256secret();

        final KonokiOAuthAuthenticator authenticator = unitOfWorkAwareProxyFactory
            .create(
                KonokiOAuthAuthenticator.class,
                new Class<?>[] { String.class, UserDAO.class },
                new Object[] { hs256key, userDAO }
            );

        final KonokiAuthorizer authorizer = unitOfWorkAwareProxyFactory
            .create(KonokiAuthorizer.class, UserDAO.class, userDAO);

        environment.jersey().register(
            new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(authenticator)
                    .setAuthorizer(authorizer)
                    .setPrefix("Bearer")
                    .buildAuthFilter()
            )
        );

        System.out.print("Got HS256 key: " + hs256key);

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new HandResource(handDAO));
        environment.jersey().register(new GameResource(gameDAO, handDAO));
        environment.jersey().register(new UserResource(userDAO));
        environment.jersey().register(new TokenResource(userDAO, hs256key));
    }

}
