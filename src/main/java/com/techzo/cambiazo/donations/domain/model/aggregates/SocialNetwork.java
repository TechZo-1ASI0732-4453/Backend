package com.techzo.cambiazo.donations.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.techzo.cambiazo.donations.domain.model.commands.CreateSocialNetworkCommand;
import com.techzo.cambiazo.donations.domain.model.valueobjects.SocialNetworkName;
import com.techzo.cambiazo.donations.domain.model.valueobjects.SocialNetworkUrl;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Represents a social network entity associated with an NGO.
 *
 * @author CambiaZo - TechZo
 * @version 1.0
 */
@Entity
public class SocialNetwork extends AuditableAbstractAggregateRoot<SocialNetwork> {

    @Embedded
    @Column(nullable = false)
    @NotNull(message = "Name is mandatory")
    private SocialNetworkName name;

    @Embedded
    @Column(nullable = false)
    @NotNull(message = "URL is mandatory")
    private SocialNetworkUrl url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ong_id")
    @NotNull(message = "Ong is mandatory")
    @JsonBackReference("ong-social-network")
    private Ong ongId;

    public SocialNetwork() {}

    /**
     * Constructor to create a SocialNetwork object from a command and an Ong entity.
     *
     * @param command The create social network command.
     * @param ong     The Ong entity associated with the social network.
     */
    public SocialNetwork(CreateSocialNetworkCommand command, Ong ong) {
        this.name = new SocialNetworkName(command.name());
        this.url = new SocialNetworkUrl(command.url());
        this.ongId = ong;
    }

    /**
     * Get the ID of the Ong associated with the social network.
     *
     * @return The ID of the Ong.
     */
    public Long getOngId() {
        return ongId.getId();
    }

    /**
     * Get the name of the social network.
     *
     * @return The name of the social network.
     */
    public String getName() {
        return name.getSocialNetworkName();
    }

    /**
     * Get the URL of the social network.
     *
     * @return The URL of the social network.
     */
    public String getUrl() {
        return url.getSocialNetworkUrl();
    }

}
