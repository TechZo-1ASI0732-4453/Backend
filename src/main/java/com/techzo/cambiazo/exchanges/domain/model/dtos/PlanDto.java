package com.techzo.cambiazo.exchanges.domain.model.dtos;

import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlanDto {
    private Long id;

    private String name;

    private String description;

    private Double price;

    List<Benefit>benefits;

    public PlanDto(Plan plan, List<Benefit>benefits){
        this.id = plan.getId();
        this.name = plan.getName();
        this.description = plan.getDescription();
        this.price = plan.getPrice();
        this.benefits = benefits;
    }
}
