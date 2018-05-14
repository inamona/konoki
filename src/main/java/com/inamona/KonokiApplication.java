package com.inamona;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class KonokiApplication extends Application<KonokiConfiguration> {

    public static void main(final String[] args) throws Exception {
        new KonokiApplication().run(args);
    }

    @Override
    public String getName() {
        return "konoki";
    }

    @Override
    public void initialize(final Bootstrap<KonokiConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final KonokiConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
