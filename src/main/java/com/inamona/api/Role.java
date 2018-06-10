package com.inamona.api;

import lombok.Getter;

import javax.persistence.*;

/**
 * @author christopher
 * @since 6/7/18
 */
@Entity
@Table(name = "roles")
@Getter
public class Role {
    /**
     * The ID of this Role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private long roleId;

    /**
     * The name of this Role.
     */
    @Column(name = "name")
    private String name;
}
