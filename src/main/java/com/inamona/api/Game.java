package com.inamona.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author christopher
 * @since 5/13/18
 */
@Getter
@AllArgsConstructor
public class Game {
    /**
     * The ID of this game.
     */
    private final int gameId;

    /**
     * The {@link LocalDateTime} at which the Game started.
     */
    private final LocalDateTime startedAt;

    /**
     * The {@link LocalDateTime} at which the Game ended.
     */
    private final LocalDateTime endedAt;

    /**
     * The {@link Hand}s in the Game.
     */
    private final List<Hand> hands;
}
