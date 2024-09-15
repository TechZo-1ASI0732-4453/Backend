package com.techzo.cambiazo.exchanges.domain.model.entities;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateUserCommand;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class User extends AuditableAbstractAggregateRoot<User> {

    @Column(nullable = false)
    @NotNull(message = "Name is required")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Email is required")
    private String email;

    @Column(nullable = false, length = 9)
    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @Column(nullable = false)
    @NotNull(message = "Password is required")
    private String password;

    @Column(nullable = false)
    @NotNull(message = "Profile picture is required")
    private String profilePicture;

    public User() {
    }

    public User(CreateUserCommand command){
        this.name = command.name();
        this.email = command.email();
        this.phoneNumber = command.phoneNumber();
        this.password = command.password();
        this.profilePicture = command.profilePicture();
    }

    public User updateInformation(String name, String email, String phoneNumber, String password, String profilePicture) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profilePicture = profilePicture;
        return this;
    }
}
