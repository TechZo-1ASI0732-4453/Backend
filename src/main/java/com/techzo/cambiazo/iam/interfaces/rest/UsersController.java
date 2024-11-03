package com.techzo.cambiazo.iam.interfaces.rest;

import com.techzo.cambiazo.iam.domain.model.queries.GetAllUsersQuery;
import com.techzo.cambiazo.iam.domain.model.queries.GetUserByEmailQuery;
import com.techzo.cambiazo.iam.domain.model.queries.GetUserByIdQuery;
import com.techzo.cambiazo.iam.domain.services.UserCommandService;
import com.techzo.cambiazo.iam.domain.services.UserQueryService;
import com.techzo.cambiazo.iam.interfaces.rest.resources.*;
import com.techzo.cambiazo.iam.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller that exposes the users resource.
 * It includes the following operations:
 * - GET /api/v1/users: returns all the users
 * - GET /api/v1/users/{userId}: returns the user with the given id
 **/
@RestController
@RequestMapping(value = "/api/v2/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
    private final UserQueryService userQueryService;

    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }


    /**
     * This method returns all the users.
     * @return a list of user resources
     * @see UserResource
     */
    @GetMapping
    public ResponseEntity<List<UserResource2>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream().map(UserResource2FromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }

    /**
     * This method returns the user with the given id.
     * @param userId the user id
     * @return the user resource with the given id
     * @throws RuntimeException if the user is not found
     * @see UserResource
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResource2> getUserById(@PathVariable Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResource2FromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @PutMapping(value = "/edit/{userId}")
    public ResponseEntity<UserResource>updateUser(@PathVariable Long userId, @RequestBody UpdateUserResource resource){
        var updateUserCommand = UpdateUserCommandFromResourceAssembler.toCommandFromResource(userId,resource);
        var user = userCommandService.handle(updateUserCommand);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @PutMapping(value = "/edit/profile/{userId}")
    public ResponseEntity<AuthenticatedUserResource>updateUserProfile(@PathVariable Long userId, @RequestBody UpdateUserProfileResource resource){
        var updateUserProfileCommand = UpdateProfileUserCommandFromResourceAssembler.toCommandFromResource(userId,resource);
        var user = userCommandService.handle(updateUserProfileCommand);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(user.get().getLeft(), user.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Void>deleteUser(@PathVariable Long userId){
        var result = userCommandService.handleDeleteUserCommand(userId);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Void> getUserByUsername(@PathVariable String username) {
        var getUserByUsernameQuery = new GetUserByEmailQuery(username);
        var user = userQueryService.handle(getUserByUsernameQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
