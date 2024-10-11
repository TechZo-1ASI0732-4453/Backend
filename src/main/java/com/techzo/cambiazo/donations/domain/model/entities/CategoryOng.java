package com.techzo.cambiazo.donations.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.techzo.cambiazo.donations.domain.model.commands.CreateCategoryOngCommand;


/**
 * Represents a category of NGOs.
 *
 * @author CambiaZo - TechZo
 * @version 1.0
 *
 */
@Setter
@Getter
@Entity
public class CategoryOng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name is required")
    private String name;

    public CategoryOng(String name) {
        this.name = name;
    }

    public CategoryOng() {
    }

    public CategoryOng(CreateCategoryOngCommand command) {
        this.name = command.name();
    }

    /**
     * Updates the information of the category.
     *
     * @param name The new name of the category.
     * @return Updated CategoryOng instance.
     */
    public CategoryOng updateInformation(String name) {
        this.name = name;
        return this;
    }

    /**
     * Retrieves the ID of the category.
     *
     * @return Category ID.
     */
    public Long getCategoryId() {
        return id;
    }

}
