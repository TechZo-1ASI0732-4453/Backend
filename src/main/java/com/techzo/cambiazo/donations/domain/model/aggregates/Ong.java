package com.techzo.cambiazo.donations.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import com.techzo.cambiazo.donations.domain.model.commands.CreateOngCommand;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Represents an NGO (Non-Governmental Organization) entity.
 *
 * @author CambiaZo - TechZo
 * @version 1.0
 */
@Entity
public class Ong extends AuditableAbstractAggregateRoot<Ong> {

    @Column(nullable = false)
    @NotNull(message = "Name is mandatory")
    @Getter
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Type is mandatory")
    @Getter
    private String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "AboutUs is mandatory")
    @Getter
    private String aboutUs;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Mission And Vision is mandatory")
    @Getter
    private String missionAndVision;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "supportForm is mandatory")
    @Getter
    private String supportForm;

    @Column(nullable = false)
    @NotNull(message = "Address is mandatory")
    @Getter
    private String address;

    @Column(nullable = false)
    @NotNull(message = "Email is mandatory")
    @Getter
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Phone is mandatory")
    @Getter
    private String phone;

    @Column(nullable = false)
    @NotNull(message = "Logo is mandatory")
    @Getter
    private String logo;

    @Column(nullable = false)
    @NotNull(message = "Website is mandatory")
    @Getter
    private String website;

    @Column(nullable = false)
    @NotNull(message = "Schedule is mandatory")
    @Getter
    private String schedule;

    @ManyToOne
    @JoinColumn(name = "category_ong_id")
    @NotNull(message = "CategoryOngId is mandatory")
    private CategoryOng categoryOngId;

    public Ong(CreateOngCommand command, CategoryOng categoryOng) {
        this.categoryOngId = categoryOng;
        this.name = command.name();
        this.type = command.type();
        this.aboutUs = command.aboutUs();
        this.missionAndVision = command.missionAndVision();
        this.supportForm = command.supportForm();
        this.address = command.address();
        this.email = command.email();
        this.phone = command.phone();
        this.logo = command.logo();
        this.website = command.website();
        this.schedule = command.schedule();
    }

    public Ong(){}

    /**
     * Updates the information of the NGO.
     *
     * @param name The name of the NGO.
     * @param type The type of the NGO.
     * @param aboutUs A description about the NGO.
     * @param missionAndVision The mission and vision of the NGO.
     * @param supportForm The support form of the NGO.
     * @param address The address of the NGO.
     * @param email The email contact of the NGO.
     * @param phone The phone contact of the NGO.
     * @param logo The logo of the NGO.
     * @param website The website URL of the NGO.
     * @param categoryOngId The category of the NGO.
     * @param schedule The schedule of the NGO.
     * @return The updated instance of Ong.
     */
    public Ong updateInformation(String name, String type, String aboutUs, String missionAndVision, String supportForm, String address, String email, String phone, String logo, String website, CategoryOng categoryOngId, String schedule) {
        this.name = name;
        this.type = type;
        this.aboutUs = aboutUs;
        this.missionAndVision = missionAndVision;
        this.supportForm = supportForm;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.logo = logo;
        this.website = website;
        this.categoryOngId = categoryOngId;
        this.schedule = schedule;
        return this;
    }

    /**
     * Get the ID of the category of the NGO.
     *
     * @return The ID of the category.
     */
    public Long getCategoryOngId() {
        return categoryOngId.getId();
    }
}
