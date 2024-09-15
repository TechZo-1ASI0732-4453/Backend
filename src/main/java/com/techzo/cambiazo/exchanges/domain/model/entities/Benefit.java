package com.techzo.cambiazo.exchanges.domain.model.entities;


import com.techzo.cambiazo.exchanges.domain.model.commands.CreateBenefitCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Benefit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name is mandatory")
    private String description;

    @ManyToOne
    @JoinColumn(name="plan_id", nullable = false)
    @NotNull(message = "Plan ID is mandatory")
    private Plan planId;


    public Benefit() {
    }

    public Benefit(CreateBenefitCommand command, Plan plan) {
        this.description = command.description();
        this.planId = plan;
    }

    public Long getPlanId() {
        return planId.getId();
    }
}
