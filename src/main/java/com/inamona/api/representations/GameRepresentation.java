package com.inamona.api.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.inamona.api.Game;
import com.inamona.resources.HandResource;
import lombok.Data;

import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

/**
 * TODO: https://stackoverflow.com/questions/24968448/jax-rs-hateoas-using-jersey-unwanted-link-properties-in-json
 *
 * @author christopher
 * @since 5/16/18
 */
@Data
public class GameRepresentation implements Serializable {

    @JsonProperty
    private final Game game;

    @JsonProperty
    private final List<Link> links;

    public GameRepresentation(final Game game) {
        this.game = game;
        this.links = game.getHands().stream()
            .map(hand -> Link.fromResource(HandResource.class).rel("hand").build(hand))
            .collect(toImmutableList());
    }
}
