package com.inamona.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * @author christopher
 * @since 5/14/18
 */
public class BasicHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
