package com.inamona.api;

import lombok.Getter;

import javax.persistence.*;

/**
 * Models a user of the application.
 *
 * @author christopher
 * @since 5/28/18
 */
@Getter
@Entity
@Table(name = "users")
public class User {
    /**
     * The ID of this User.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private long userId;


}
