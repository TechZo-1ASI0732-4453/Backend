package com.techzo.cambiazo.iam.domain.model.aggregates;

import com.techzo.cambiazo.iam.domain.model.entities.Role;
import com.techzo.cambiazo.iam.domain.model.valueobjects.BanStatus;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
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
    @Size(max = 70)
    @Email // Validating the username as email
    @Column(unique = true)
    private String username; // Used as email

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 9)
    @Column(nullable = true)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_google_account", nullable = false)
    private Boolean isGoogleAccount = false;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "active", column = @Column(name = "is_banned_from_posting", nullable = false)),
            @AttributeOverride(name = "bannedUntil", column = @Column(name = "banned_until"))
    })
    private BanStatus banStatus = BanStatus.inactive();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
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
        this.isGoogleAccount = false;
        this.roles = new HashSet<>();
    }


    public User(String username, String password, String name, String phoneNumber, String profilePicture, boolean isGoogleAccount) {
        this(username, password, name, phoneNumber, profilePicture);
        this.isGoogleAccount = isGoogleAccount;
    }

    public User(String username, String password, String name, String phoneNumber, String profilePicture, boolean isGoogleAccount, List<Role> roles) {
        this(username, password, name, phoneNumber, profilePicture, isGoogleAccount);
        addRoles(roles);
    }

    public User updateInformation(String username, String password, String name, String phoneNumber, String profilePicture, boolean isActive) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.isActive = isActive;
        return this;
    }

    public User updatedPassword(String password) {
        this.password = password;
        return this;
    }

    public User updateProfileInformation(String username, String name, String phoneNumber, String profilePicture) {
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        return this;
    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        return this;
    }

    public void banFor(Duration duration) {
        this.banStatus = BanStatus.activeFor(duration);
    }

    public boolean canPost() {
        this.banStatus = banStatus.refresh();
        return !banStatus.isActive();
    }

    public void unban() {
        this.banStatus = BanStatus.inactive();
    }

    public User updateBanStatus(Boolean active, Duration duration) {
        if (active) {
            this.banStatus = BanStatus.activeFor(duration);
        } else {
            this.banStatus = BanStatus.inactive();
        }
        return this;
    }


}
