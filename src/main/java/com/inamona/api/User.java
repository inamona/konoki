package com.inamona.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * The User's salt.
     */
    @JsonIgnore
    @Column(name = "salt", nullable = false)
    private String salt;

    /**
     * The User's hashed password.
     */
    @JsonIgnore
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    /**
     * The User's roles.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Role> roles;

    /**
     * The {@link Date} at which the User was created.
     */
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * The {@link Date} at which the User was last logged in.
     */
    @Column(name = "last_login", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;

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

    public boolean passwordMatches(final String password) {
        return this.hashedPassword.equals(BCrypt.hashpw(password, this.salt));
    }
}
