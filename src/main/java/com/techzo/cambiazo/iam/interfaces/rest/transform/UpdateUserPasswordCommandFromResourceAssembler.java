package com.techzo.cambiazo.iam.interfaces.rest.transform;

import com.techzo.cambiazo.iam.domain.model.commands.UpdateUserPasswordCommand;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UpdateUserPasswordResource;

public class UpdateUserPasswordCommandFromResourceAssembler {
    public static UpdateUserPasswordCommand toCommandFromResource(String username,UpdateUserPasswordResource resource) {
        return new UpdateUserPasswordCommand(username,resource.newPassword());
    }
}
