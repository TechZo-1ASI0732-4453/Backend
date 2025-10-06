package com.techzo.cambiazo.exchanges.application.internal.outboundservices;

import com.techzo.cambiazo.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

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
}
