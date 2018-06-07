package com.inamona.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.security.Principal;

/**
 * Models a user of the application.
 *
 * @author christopher
 * @since 5/28/18
 */
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements Principal {
    /**
     * The ID of this User.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @JsonIgnore
    private long userId;

    /**
     * The email address of this User.
     */
    @NaturalId
    @Column(name = "email", updatable = true, nullable = false, unique = true)
    private String email;

    /**
     * The User's salt.
     */
    @JsonIgnore
    @Column(name = "salt", updatable = true, nullable = false)
    private String salt;

    /**
     * The User's hashed password.
     */
    @JsonIgnore
    @Column(name = "hashed_password", updatable = true, nullable = false)
    private String hashedPassword;

    /**
     * New instance.
     * @param email the email address of this User.
     * @param salt the BCrypt salt.
     * @param hashedPassword the hashed password of this User.
     */
    public User(final String email, final String salt, final String hashedPassword) {
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String getName() {
        return this.email;
    }
}
