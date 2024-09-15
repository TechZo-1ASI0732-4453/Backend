package com.techzo.cambiazo.exchanges.domain.model.entities;


import com.techzo.cambiazo.exchanges.domain.model.commands.CreateCountryCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "name is required")
    private String name;

    public Country() {
    }

    public Country(CreateCountryCommand command) {
        this.name = command.name();
    }

}
