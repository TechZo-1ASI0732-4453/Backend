package com.techzo.cambiazo.exchanges.domain.model.dtos;

import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import com.techzo.cambiazo.exchanges.domain.model.entities.Subscription;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UserResource2;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
public class SubscriptionDto {

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String state;

    private Long userId;

    private PlanDto plan;

    public SubscriptionDto(){
    }

    public SubscriptionDto(Subscription subscription,PlanDto plan){
        this.id = subscription.getId();
        this.startDate = subscription.getStartDate();
        this.endDate = subscription.getEndDate();
        this.state = subscription.getState();
        this.userId = subscription.getUserId();
        this.plan = plan;
    }
}
