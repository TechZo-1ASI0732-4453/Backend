package com.techzo.cambiazo.exchanges.domain.model.entities;


import com.techzo.cambiazo.exchanges.domain.model.commands.CreateSubscriptionCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @NotNull(message="State is mandatory")
    private String state;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    @NotNull(message="Plan ID is mandatory")
    private Plan planId;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @NotNull(message="User ID is mandatory")
    private User userId;

    public Subscription() {
    }

    public Subscription(CreateSubscriptionCommand command, Plan plan, User user) {
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusMonths(1);
        this.state = command.state();
        this.planId = plan;
        this.userId = user;
    }

    public Subscription updateInformation(LocalDate startDate, LocalDate endDate, String state, Plan plan, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.planId = plan;
        this.userId = user;
        return this;
    }

    public Long getPlanId() {
        return planId.getId();
    }

    public Long getUserId() {
        return userId.getId();
    }

}
