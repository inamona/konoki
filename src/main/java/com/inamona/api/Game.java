package com.inamona.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.inamona.resources.GameResource;
import com.inamona.resources.HandResource;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Models a game.
 *
 * @author christopher
 * @since 5/13/18
 */
@Getter
@Entity
@Table(name = "games")
@NamedQueries(
    @NamedQuery(
        name = "com.inamona.api.Game.findAll",
        query = "from Game g"
    )
)
public class Game {
    /**
     * The ID of this game.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long gameId;

    /**
     * The {@link Date} at which the Game started.
     */
    @Column(name = "started_at", updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;

    /**
     * The {@link Date} at which the Game ended.
     */
    @Column(name = "ended_at")
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endedAt;

    /**
     * The {@link Hand}s in the Game.
     */
    @OneToMany
    @JsonIgnore
    private List<Hand> hands = Lists.newArrayList();

    @Transient
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonSerialize
    @JsonDeserialize
    private List<Link> links = Lists.newArrayList();

    /**
     * Adds a {@link Hand} to this Game.
     * @return a new {@link Hand}.
     */
    public Hand addHand() {
        final Hand hand = new Hand();
        this.hands.add(hand);
        hand.setGame(this);
        return hand;
    }

    /**
     * Removes a {@link Hand} from this Game.
     * @param hand the {@link Hand} to remove.
     */
    public void removeHand(final Hand hand) {
        this.hands.remove(hand);
        hand.setGame(null);
    }

    /**
     * Adds a {@link Link} to this Game's links.
     * @param link the Link to add.
     */
    public void addLink(final Link link) {
        this.links.add(link);
    }

    /**
     * Convenience method to get all the {@link Link}s for a given {@link Game}.
     * @param uriInfo the {@link UriInfo} from the incoming request.
     * @return the {@link Link}s for the {@link Game}.
     */
    public void setLinks(final UriInfo uriInfo) {
        final URI selfHref = uriInfo.getBaseUriBuilder()
            .path(GameResource.class)
            .path(String.valueOf(this.getGameId()))
            .build();

        final List<Link> links = new ArrayList<>();
        links.add(new Link(selfHref, "self"));
        this.getHands().forEach(hand -> {
            final UriBuilder handHrefBuilder = uriInfo.getAbsolutePathBuilder().path(HandResource.class);
            links.add(new Link(handHrefBuilder.path(String.valueOf(hand.getHandId())).build(), "hand"));
        });
        this.links = links;
    }
}
