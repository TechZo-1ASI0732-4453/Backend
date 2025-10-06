package com.techzo.cambiazo.iam.application.internal.commandservices;

import com.techzo.cambiazo.iam.domain.model.commands.BanUserCommand;
import com.techzo.cambiazo.iam.domain.model.commands.UnbanUserCommand;
import com.techzo.cambiazo.iam.domain.model.queries.GetUserByIdQuery;
import com.techzo.cambiazo.iam.domain.services.UserQueryService;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Service for handling user ban commands
 */
@Service
public class UserBanCommandServiceImpl {

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    public UserBanCommandServiceImpl(UserRepository userRepository, UserQueryService userQueryService) {
        this.userRepository = userRepository;
        this.userQueryService = userQueryService;
    }

    /**
     * Bans a user for the specified duration
     * @param command The ban command containing user ID and duration
     * @return true if user was successfully banned, false if user not found
     */
    public boolean banUser(BanUserCommand command) {
        var getUserQuery = new GetUserByIdQuery(command.userId());
        var userResult = userQueryService.handle(getUserQuery);
        
        if (userResult.isEmpty()) {
            return false;
        }
        
        var user = userResult.get();
        user.banFor(command.banDuration());
        
        // Save the updated user
        userRepository.save(user);
        return true;
    }

    /**
     * Unbans a user
     * @param command The unban command containing user ID
     * @return true if user was successfully unbanned, false if user not found
     */
    public boolean unbanUser(UnbanUserCommand command) {
        var getUserQuery = new GetUserByIdQuery(command.userId());
        var userResult = userQueryService.handle(getUserQuery);
        
        if (userResult.isEmpty()) {
            return false;
        }
        
        var user = userResult.get();
        user.unban();
        
        // Save the updated user
        userRepository.save(user);
        return true;
    }

    /**
     * Checks if a user is currently banned
     * @param userId The user ID to check
     * @return true if user is banned, false otherwise
     */
    public boolean isUserBanned(Long userId) {
        var getUserQuery = new GetUserByIdQuery(userId);
        var userResult = userQueryService.handle(getUserQuery);
        
        if (userResult.isEmpty()) {
            return false;
        }
        
        var user = userResult.get();
        return !user.canPost();
    }

    /**
     * Gets the remaining ban duration for a user
     * @param userId The user ID
     * @return Duration remaining, or Duration.ZERO if not banned
     */
    public Duration getRemainingBanDuration(Long userId) {
        var getUserQuery = new GetUserByIdQuery(userId);
        var userResult = userQueryService.handle(getUserQuery);
        
        if (userResult.isEmpty()) {
            return Duration.ZERO;
        }
        
        var user = userResult.get();
        return user.getBanStatus().getRemainingTime();
    }
}
