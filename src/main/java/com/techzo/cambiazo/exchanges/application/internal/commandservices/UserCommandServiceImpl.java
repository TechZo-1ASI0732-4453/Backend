package com.techzo.cambiazo.exchanges.application.internal.commandservices;


import com.techzo.cambiazo.exchanges.domain.model.commands.CreateUserCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateUserCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.User;
import com.techzo.cambiazo.exchanges.domain.services.IUserCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements IUserCommandService {

    private final IUserRepository userRepository;

    public UserCommandServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User>handle(CreateUserCommand command){
        if(userRepository.existsByEmail(command.email())){
            throw new IllegalArgumentException("User with same email already exists");
        }
        if(userRepository.existsByPhoneNumber(command.phoneNumber())){
            throw new IllegalArgumentException("User with same phone number already exists");
        }
        var user = new User(command);
        var createdUser = userRepository.save(user);
        return Optional.of(createdUser);
    }

    @Override
    public Optional<User>handle(UpdateUserCommand command){
        var result=userRepository.findById(command.id());
        if(result.isEmpty()){
            throw new IllegalArgumentException("User does not exist");
        }

        var userToUpdate=result.get();
        try{
            var updatedUser=userRepository.save(userToUpdate.updateInformation(
                    command.name(), command.email(), command.phoneNumber(), command.password(), command.profilePicture()));
            return Optional.of(updatedUser);
        }catch(Exception e){
            throw new IllegalArgumentException("Error while updating user: "+e.getMessage());
        }
    }

    @Override
    public boolean handleDeleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        } else {
            throw new IllegalArgumentException("User does not exist");
        }
    }
}
