package com.inamona.api.filters.authentication;

import com.inamona.api.Role;
import com.inamona.api.User;
import com.inamona.db.UserDAO;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;

import static com.google.common.collect.ImmutableList.toImmutableList;

/**
 * @author christopher
 * @since 6/7/18
 */
@AllArgsConstructor
public class KonokiAuthorizer implements Authorizer<User> {
    /**
     * The UserDAO.
     */
    private final UserDAO userDAO;

    @Override
    @UnitOfWork
    public boolean authorize(final User principal, final String role) {
        final User fromDB = this.userDAO.findByEmail(principal.getEmail());
        return fromDB.getRoles().stream().map(Role::getName).collect(toImmutableList()).contains(role);
    }
}
