package com.techzo.cambiazo.exchanges.domain.model.entities;


import com.techzo.cambiazo.exchanges.domain.model.commands.CreateSubscriptionCommand;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

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
        this.startDate = LocalDateTime.now();
        this.state = command.state();
        this.planId = plan;
        if(planId.getId() == 1){
            this.endDate =  startDate.plusYears(3);
        } else {
            this.endDate = startDate.plusMonths(1);
        }
        this.userId = user;
    }

    public Subscription updateInformation(LocalDateTime startDate, LocalDateTime endDate, String state, Plan plan, User user) {
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
