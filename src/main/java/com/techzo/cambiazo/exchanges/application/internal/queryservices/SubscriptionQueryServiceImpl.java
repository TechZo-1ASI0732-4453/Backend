package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.dtos.PlanDto;
import com.techzo.cambiazo.exchanges.domain.model.dtos.SubscriptionDto;
import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import com.techzo.cambiazo.exchanges.domain.model.entities.Subscription;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllSubscriptionsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetSubscriptionByIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetActiveSubscriptionByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.ISubscriptionQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IBenefitRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IPlanRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.ISubscriptionRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UserResource2;
import com.techzo.cambiazo.iam.interfaces.rest.transform.UserResource2FromEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SubscriptionQueryServiceImpl implements ISubscriptionQueryService {

    private final ISubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;

    private final IPlanRepository planRepository;

    private final IBenefitRepository benefitRepository;

    public SubscriptionQueryServiceImpl(ISubscriptionRepository subscriptionRepository, UserRepository userRepository, IPlanRepository planRepository, IBenefitRepository benefitRepository){
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.benefitRepository = benefitRepository;
    }

    @Override
    public Optional<SubscriptionDto> handle(GetSubscriptionByIdQuery query) {
        Subscription subscription = this.subscriptionRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("Subscription with id "+query.id()+" not found"));

        Plan plan = this.planRepository.findById(subscription.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan with id "+subscription.getPlanId()+" not found"));

        List<Benefit>benefits= this.benefitRepository.findBenefitsByPlanId(plan);

        PlanDto planDto = new PlanDto(plan, benefits);

        return Optional.of(new SubscriptionDto(subscription, planDto));
    }

    @Override
    public Optional<SubscriptionDto> handle(GetActiveSubscriptionByUserIdQuery query) {
        User user = this.userRepository.findById(query.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with id "+query.userId()+" not found"));

        Subscription subscription = this.subscriptionRepository.findSubscriptionActiveByUserId(user)
                .orElseThrow(() -> new IllegalArgumentException("Active subscription for user with id "+query.userId()+" not found"));

        Plan plan = this.planRepository.findById(subscription.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan with id "+subscription.getPlanId()+" not found"));

        List<Benefit>benefits= this.benefitRepository.findBenefitsByPlanId(plan);

        PlanDto planDto = new PlanDto(plan, benefits);

        return Optional.of(new SubscriptionDto(subscription, planDto));
    }

    @Override
    public List<SubscriptionDto> handle(GetAllSubscriptionsQuery query) {
        List<Subscription>subscriptions = this.subscriptionRepository.findAll();

        return subscriptions.stream().map(subscription->{
            Plan plan = this.planRepository.findById(subscription.getPlanId())
                    .orElseThrow(() -> new IllegalArgumentException("Plan with id "+subscription.getPlanId()+" not found"));

            List<Benefit>benefits= this.benefitRepository.findBenefitsByPlanId(plan);

            PlanDto planDto = new PlanDto(plan, benefits);
            return new SubscriptionDto(subscription, planDto);
        }).toList();
    }
}
