package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.application.internal.outboundservices.IamAclOutboundService;
import com.techzo.cambiazo.exchanges.domain.model.commands.ProcessContentViolationCommand;
import org.springframework.stereotype.Service;

/**
 * Service for handling content violation commands
 */
@Service
public class ContentViolationCommandServiceImpl {

    private final IamAclOutboundService iamAclOutboundService;

    public ContentViolationCommandServiceImpl(IamAclOutboundService iamAclOutboundService) {
        this.iamAclOutboundService = iamAclOutboundService;
    }

    /**
     * Processes a content violation by banning the user
     * @param command The content violation command
     * @return true if user was successfully banned, false otherwise
     */
    public boolean processContentViolation(ProcessContentViolationCommand command) {
        try {
            // Get ban duration from violation type
            int banDurationMinutes = command.violationType().getBanDurationMinutes();
            
            // Ban the user through IAM
            boolean banResult = iamAclOutboundService.banUser(command.userId(), banDurationMinutes);
            
            if (banResult) {
                // Log the violation for audit purposes
                logContentViolation(command);
                return true;
            }
            
            return false;
        } catch (Exception e) {
            // Log error but don't expose internal details
            System.err.println("Error processing content violation for user " + command.userId() + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a user is currently banned
     * @param userId The user ID to check
     * @return true if user is banned, false otherwise
     */
    public boolean isUserBanned(Long userId) {
        return iamAclOutboundService.isUserBanned(userId);
    }

    /**
     * Gets the remaining ban duration for a user
     * @param userId The user ID
     * @return Minutes remaining, or 0 if not banned
     */
    public long getRemainingBanMinutes(Long userId) {
        return iamAclOutboundService.getRemainingBanMinutes(userId);
    }

    /**
     * Logs content violation for audit purposes
     * @param command The violation command
     */
    private void logContentViolation(ProcessContentViolationCommand command) {
        System.out.println(String.format(
            "Content violation processed - User: %d, Type: %s, Reason: %s, Ban Duration: %d minutes",
            command.userId(),
            command.violationType().name(),
            command.violationReason(),
            command.violationType().getBanDurationMinutes()
        ));
    }
}
