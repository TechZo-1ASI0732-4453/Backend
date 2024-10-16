package com.techzo.cambiazo.exchanges.domain.model.entities;
import com.techzo.cambiazo.exchanges.domain.model.commands.CreateReviewCommand;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Review extends AuditableAbstractAggregateRoot<Review> {

    @Column(name = "message", columnDefinition = "TEXT")
    @NotNull(message = "Message is mandatory")
    private String message;

    @Column(name = "rating", nullable = false)
    @NotNull(message = "Rating is mandatory")
    private Integer rating;

    @Column(name = "state", nullable = false)
    @NotNull(message = "State is mandatory")
    private String state;

    @ManyToOne
    @JoinColumn(name = "exchange_id", nullable = false)
    private Exchange exchangeId;

    @ManyToOne
    @JoinColumn(name = "user_author_id", nullable = false)
    private User userAuthorId;

    @ManyToOne
    @JoinColumn(name = "user_receptor_id", nullable = false)
    private User userReceptorId;

    public Review() {
    }

    public Review(CreateReviewCommand command, Exchange exchangeId, User userAuthorId, User userReceptorId) {
        this.message = command.message();
        this.rating = command.rating();
        this.state = command.state();
        this.exchangeId = exchangeId;
        this.userAuthorId = userAuthorId;
        this.userReceptorId = userReceptorId;
    }

    public Long getExchangeId() {
        return exchangeId.getId();
    }

    public Long getUserAuthorId() {
        return userAuthorId.getId();
    }

    public Long getUserReceptorId() {
        return userReceptorId.getId();
    }


}
