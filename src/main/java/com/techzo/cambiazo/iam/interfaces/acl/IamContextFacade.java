package com.techzo.cambiazo.iam.interfaces.acl;

import com.techzo.cambiazo.iam.domain.model.commands.BanUserCommand;
import com.techzo.cambiazo.iam.domain.model.commands.SignUpCommand;
import com.techzo.cambiazo.iam.domain.model.commands.UnbanUserCommand;
import com.techzo.cambiazo.iam.domain.model.entities.Role;
import com.techzo.cambiazo.iam.domain.model.queries.GetUserByIdQuery;
import com.techzo.cambiazo.iam.domain.model.queries.GetUserByUsernameQuery;
import com.techzo.cambiazo.iam.domain.services.UserCommandService;
import com.techzo.cambiazo.iam.domain.services.UserQueryService;
import com.techzo.cambiazo.iam.application.internal.commandservices.UserBanCommandServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * IamContextFacade
 * <p>
 *     This class is a facade for the IAM context. It provides a simple interface for other bounded contexts to interact with the
 *     IAM context.
 *     This class is a part of the ACL layer.
 * </p>
 *
 */
@Service
public class IamContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final UserBanCommandServiceImpl userBanCommandService;

    public IamContextFacade(UserCommandService userCommandService, UserQueryService userQueryService, UserBanCommandServiceImpl userBanCommandService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.userBanCommandService = userBanCommandService;
    }

    /**
     * Creates a user with the given username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The id of the created user.
     */
    public Long createUser(String username, String password,  String name, String phoneNumber, String profilePicture, boolean isGoogleAccount) {
        var signUpCommand = new SignUpCommand(username, password, name, phoneNumber, profilePicture, isGoogleAccount, List.of(Role.getDefaultRole()));
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    /**
     * Creates a user with the given username, password and roles.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param roleNames The names of the roles of the user. When a role does not exist, it is ignored.
     * @return The id of the created user.
     */
    public Long createUser(String username, String password, String name, String phoneNumber, String profilePicture, boolean isGoogleAccount, List<String> roleNames) {
        var roles = roleNames != null ? roleNames.stream().map(Role::toRoleFromName).toList() : new ArrayList<Role>();
        var signUpCommand = new SignUpCommand(username, password, name, phoneNumber, profilePicture, isGoogleAccount, roles);
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    /**
     * Fetches the id of the user with the given username.
     * @param username The username of the user.
     * @return The id of the user.
     */
    public Long fetchUserIdByUsername(String username) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var result = userQueryService.handle(getUserByUsernameQuery);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    /**
     * Fetches the username of the user with the given id.
     * @param userId The id of the user.
     * @return The username of the user.
     */
    public String fetchUsernameByUserId(Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        if (result.isEmpty()) return Strings.EMPTY;
        return result.get().getUsername();
    }

    /**
     * Validates if a user exists with the given id.
     * @param userId The id of the user to validate.
     * @return true if the user exists, false otherwise.
     */
    public boolean validateUserExists(Long userId) {
        if (userId == null || userId <= 0) return false;
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        return result.isPresent();
    }

    /**
     * Bans a user for the specified duration
     * @param userId The user ID to ban
     * @param banDurationMinutes The ban duration in minutes
     * @return true if user was successfully banned, false if user not found
     */
    public boolean banUser(Long userId, int banDurationMinutes) {
        Duration duration = Duration.ofMinutes(banDurationMinutes);
        var banCommand = new BanUserCommand(userId, duration);
        return userBanCommandService.banUser(banCommand);
    }

    /**
     * Unbans a user
     * @param userId The user ID to unban
     * @return true if user was successfully unbanned, false if user not found
     */
    public boolean unbanUser(Long userId) {
        var unbanCommand = new UnbanUserCommand(userId);
        return userBanCommandService.unbanUser(unbanCommand);
    }

    /**
     * Checks if a user is currently banned
     * @param userId The user ID to check
     * @return true if user is banned, false otherwise
     */
    public boolean isUserBanned(Long userId) {
        return userBanCommandService.isUserBanned(userId);
    }

    /**
     * Gets the remaining ban duration for a user in minutes
     * @param userId The user ID
     * @return Minutes remaining, or 0 if not banned
     */
    public long getRemainingBanMinutes(Long userId) {
        Duration remaining = userBanCommandService.getRemainingBanDuration(userId);
        return remaining.toMinutes();
    }

}
