package com.techzo.cambiazo.exchanges.domain.model.entities;


import com.techzo.cambiazo.exchanges.domain.model.commands.CreatePlanCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name is mandatory")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Description is mandatory")
    private String description;


    @Column(nullable = false)
    @NotNull(message = "Price is mandatory")
    private Double price;

    public Plan(){}

    public Plan(CreatePlanCommand command){
        this.name = command.name();
        this.description = command.description();
        this.price = command.price();
    }
}
