package com.techzo.cambiazo.iam.domain.model.aggregates;

import com.techzo.cambiazo.iam.domain.model.commands.UpdateUserCommand;
import com.techzo.cambiazo.iam.domain.model.entities.Role;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    @NotBlank
    @Size(max = 50)
    @Email // Validating the username as email
    @Column(unique = true)
    private String username; // Used as email

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 9)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String username, String password, String name, String phoneNumber, String profilePicture) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.roles = new HashSet<>();
    }

    public User(String username, String password, String name, String phoneNumber, String profilePicture, List<Role> roles) {
        this(username, password, name, phoneNumber, profilePicture);
        addRoles(roles);
    }

    public User updateInformation(String username, String password, String name, String phoneNumber, String profilePicture){
        this.username=username;
        this.password=password;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.profilePicture=profilePicture;
        return this;
    }

    public User updateProfileInformation(String username,String name, String phoneNumber, String profilePicture){
        this.username=username;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.profilePicture=profilePicture;
        return this;
    }

    /**
     * Add a role to the user
     * @param role the role to add
     * @return the user with the added role
     */
    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    /**
     * Add a list of roles to the user
     * @param roles the list of roles to add
     * @return the user with the added roles
     */
    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        return this;
    }
}
