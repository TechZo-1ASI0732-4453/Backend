package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllUsersQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetUserByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IUserCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IUserQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateUserResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UpdateUserResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UserResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v2/users")
@Tag(name = "Users", description = "Users Management Endpoints")
public class UserController {

    private final IUserCommandService userCommandService;

    private final IUserQueryService userQueryService;

    public UserController(IUserCommandService userCommandService, IUserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @Operation(summary = "Create a new User", description = "Create a new User with the input data.")
    @PostMapping
    public ResponseEntity<UserResource>createUser(@RequestBody CreateUserResource resource){
        try{
            var createUserCommand = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
            var user = userCommandService.handle(createUserCommand);
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return ResponseEntity.status(CREATED).body(userResource);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update a User", description = "Update a User with the input data.")
    @PutMapping("/edit/{userId}")
    public ResponseEntity<UserResource> updateUser(@PathVariable Long userId, @RequestBody UpdateUserResource resource) {
        try {
            var updateUserCommand = UpdateUserCommandFromResourceAssembler.toCommandFromResource(userId,resource);
            var user = userCommandService.handle(updateUserCommand);
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return ResponseEntity.ok(userResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResource>getUserById(@PathVariable Long id){
        try {
            var getUserById = new GetUserByIdQuery(id);
            var user = userQueryService.handle(getUserById);
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return ResponseEntity.ok(userResource);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResource>>getAllUsers(){
        try {
            var getAllUsersQuery = new GetAllUsersQuery();
            var users = userQueryService.handle(getAllUsersQuery);
            var userResource = users.stream()
                    .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(userResource);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Delete a User", description = "Delete a User with the input data.")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userCommandService.handleDeleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
