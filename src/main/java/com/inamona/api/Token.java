package com.inamona.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author christopher
 * @since 6/6/18
 */
@Entity
@Table(name = "tokens")
public class Token {
    /**
     * The API token.
     */
    @Id
    @Column(name = "token", updatable = false, nullable = false)
    @Getter
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    /**
     * The {@link LocalDateTime} at which the Token was issued.
     */
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "issued_at", updatable = false)
    private LocalDateTime issuedAt;

    /**
     * The {@link LocalDateTime} at which the Token expires.
     */
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "expires_at", updatable = false)
    private LocalDateTime expiresAt;

    /**
     * New instance.
     * @param user the User.
     * @param token the API token.
     */
    public Token(final User user, final String token) {
        this.user = user;
        this.token = token;
    }
}
