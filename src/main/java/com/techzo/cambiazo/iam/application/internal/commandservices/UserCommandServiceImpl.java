package com.techzo.cambiazo.iam.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.FavoriteProduct;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.entities.Subscription;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.*;
import com.techzo.cambiazo.iam.application.internal.outboundservices.hashing.HashingService;
import com.techzo.cambiazo.iam.application.internal.outboundservices.tokens.TokenService;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.domain.model.commands.SignInCommand;
import com.techzo.cambiazo.iam.domain.model.commands.SignUpCommand;
import com.techzo.cambiazo.iam.domain.model.commands.UpdateProfileUserCommand;
import com.techzo.cambiazo.iam.domain.model.commands.UpdateUserCommand;
import com.techzo.cambiazo.iam.domain.services.UserCommandService;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    private final IFavoriteProductRepository favoriteProductRepository;

    private final IProductRepository productRepository;

    private final ISubscriptionRepository subscriptionRepository;

    private final RoleRepository roleRepository;


    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository, IFavoriteProductRepository favoriteProductRepository, IProductRepository productRepository, ISubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.favoriteProductRepository = favoriteProductRepository;
        this.productRepository = productRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the username and password
     * @return and optional containing the user matching the username and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the user.
     * </p>
     * @param command the sign-up command containing the username and password
     * @return the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");
        var roles = command.roles().stream().map(role -> roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Role name not found"))).toList();
        var user = new User(command.username(), hashingService.encode(command.password()), command.name(), command.phoneNumber(), command.profilePicture(),roles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }

    @Override
    public Optional<User>handle(UpdateUserCommand command){
        var user = userRepository.findById(command.userId());
        if (user.isEmpty())
            throw new RuntimeException("User not found");

        var userToUpdate = user.get();

        try {
            var updateUser = userRepository.save(userToUpdate.updateInformation(command.username(), hashingService.encode(command.password()), command.name(), command.phoneNumber(), command.profilePicture(), command.isActive()));
            return Optional.of(updateUser);
        }catch (Exception e){
            throw new RuntimeException("Error while updating user: " + e.getMessage());
        }
    }

    @Override
    public Optional<ImmutablePair<User, String>>handle(UpdateProfileUserCommand command){
        var user = userRepository.findById(command.userId());
        if (user.isEmpty())
            throw new RuntimeException("User not found");

        var userToUpdate = user.get();

        try {
            var updateUser = userRepository.save(userToUpdate.updateProfileInformation(command.username(),command.name(), command.phoneNumber(), command.profilePicture()));
            var token = tokenService.generateToken(user.get().getUsername());
            return Optional.of(ImmutablePair.of(user.get(), token));
        }catch (Exception e){
            throw new RuntimeException("Error while updating user: " + e.getMessage());
        }
    }

    @Override
    public boolean handleDeleteUserCommand(Long id){
        var user = userRepository.findById(id);
        if (user.isEmpty())
            throw new RuntimeException("User not found");

        var userToDelete = user.get();

        try{
            List<FavoriteProduct> favoriteProducts = favoriteProductRepository.findFavoriteProductsByUserId(userToDelete);
            favoriteProductRepository.deleteAll(favoriteProducts);

            productRepository.updateProductAvailabilityByUser(userToDelete);
            List<Subscription> subscriptions = subscriptionRepository.findAllByUserId(userToDelete);
            subscriptionRepository.deleteAll(subscriptions);
            userRepository.save(
                    userToDelete.updateInformation(
                            "deleted_"+ UUID.randomUUID() + "@cambiazo.com"
                            ,hashingService.encode(UUID.randomUUID().toString())
                            ,"Usuario de Cambiazo"
                            , "000000000"
                            ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6lqpQj3oAmc1gtyM78oJCbTaDrD7Fj9NRlceOPDZiHA&s"
                            ,false
                    )
            );
            return true;
        }catch (Exception e){
            throw new RuntimeException("Error while deleting user: " + e.getMessage());
        }
    }

}
