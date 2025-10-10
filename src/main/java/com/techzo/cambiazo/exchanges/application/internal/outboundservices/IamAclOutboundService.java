package com.techzo.cambiazo.exchanges.application.internal.outboundservices;

import com.techzo.cambiazo.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ACL Outbound Service for communicating with IAM context
 */
@Service
public class IamAclOutboundService {
    
    private final IamContextFacade iamContextFacade;
    
    public IamAclOutboundService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }
    
    /**
     * Validates if a user exists in the IAM context
     * @param userId The user ID to validate
     * @return true if user exists, false otherwise
     */
    public boolean validateUserExists(Long userId) {
        return iamContextFacade.validateUserExists(userId);
    }
    
    /**
     * Gets username by user ID from IAM context
     * @param userId The user ID
     * @return username if user exists, empty string otherwise
     */
    public String getUsernameByUserId(Long userId) {
        return iamContextFacade.fetchUsernameByUserId(userId);
    }

    /**
     * Bans a user for the specified duration
     * @param userId The user ID to ban
     * @param banDurationMinutes The ban duration in minutes
     * @return true if user was successfully banned, false otherwise
     */
    public boolean banUser(Long userId, int banDurationMinutes) {
        return iamContextFacade.banUser(userId, banDurationMinutes);
    }

    /**
     * Unbans a user
     * @param userId The user ID to unban
     * @return true if user was successfully unbanned, false otherwise
     */
    public boolean unbanUser(Long userId) {
        return iamContextFacade.unbanUser(userId);
    }

    /**
     * Checks if a user is currently banned
     * @param userId The user ID to check
     * @return true if user is banned, false otherwise
     */
    public boolean isUserBanned(Long userId) {
        return iamContextFacade.isUserBanned(userId);
    }

    /**
     * Gets the remaining ban duration for a user in minutes
     * @param userId The user ID
     * @return Minutes remaining, or 0 if not banned
     */
    public long getRemainingBanMinutes(Long userId) {
        return iamContextFacade.getRemainingBanMinutes(userId);
    }

    /**
     * Gets the ban status information for a user
     * @param userId The user ID
     * @return A map containing isBanned (Boolean) and bannedUntil (LocalDateTime)
     */
    public Map<String, Object> getUserBanInfo(Long userId) {
        return iamContextFacade.getUserBanInfo(userId);
    }

}
