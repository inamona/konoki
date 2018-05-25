package com.inamona.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

/**
 * HATEOAS links.
 *
 * @author christopher
 * @since 5/19/18
 */
@Getter
@Setter
@AllArgsConstructor
public class Link {
    /**
     * The Link URI.
     */
    private final URI href;
    /**
     * The Link rel.
     */
    private final String rel;
}
