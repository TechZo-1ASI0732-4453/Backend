package com.techzo.cambiazo.donations.application.internal.commandservices;


import org.springframework.stereotype.Service;
import com.techzo.cambiazo.donations.domain.exceptions.CategoryOngNotFoundException;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.commands.CreateOngCommand;
import com.techzo.cambiazo.donations.domain.model.commands.UpdateOngCommand;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.domain.services.OngCommandService;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.CategoryOngRepository;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.OngRepository;

import java.util.Optional;

@Service
public class OngCommandServiceImpl implements OngCommandService{

    private final OngRepository ongRepository;

    private final CategoryOngRepository categoryOngRepository;

    public OngCommandServiceImpl(OngRepository ongRepository, CategoryOngRepository categoryOngRepository) {
        this.ongRepository = ongRepository;
        this.categoryOngRepository = categoryOngRepository;
    }

    @Override
    public Optional<Ong> handle(CreateOngCommand command){
        CategoryOng categoryOng = categoryOngRepository.findById(command.categoryOngId())
                .orElseThrow(() -> new CategoryOngNotFoundException(command.categoryOngId()));

        var name = command.name();
        ongRepository.findByName(name).ifPresent(ong ->{
            throw new IllegalArgumentException("Ong with name already exists");
        });

        var email = command.email();
        ongRepository.findByEmail(email).ifPresent(ong ->{
            throw new IllegalArgumentException("Ong with email already exists");
        });

        var ong = new Ong(command, categoryOng);
        ongRepository.save(ong);
        return Optional.of(ong);
    }

    @Override
    public Optional<Ong>handle(UpdateOngCommand command){
        var result = ongRepository.findById(command.id());
        if(result.isEmpty()){
            throw new IllegalArgumentException("Ong does not exist");
        }
        var ongToUpdate = result.get();

        //String name, String type, String aboutUs, String missionAndVision, String supportForm, String address, String email, String phone, String logo, String website, CategoryOng categoryOngId
        try{
            CategoryOng categoryOng = categoryOngRepository.findById(command.categoryOngId())
                    .orElseThrow(() -> new CategoryOngNotFoundException(command.categoryOngId()));
            var updatedOng = ongRepository.save(ongToUpdate.updateInformation(command.name(), command.type(), command.aboutUs(), command.missionAndVision(),
                    command.supportForm(), command.address(), command.email(), command.phone(), command.logo(), command.website(), categoryOng, command.schedule()));
            return Optional.of(updatedOng);
        }catch (Exception e){
            throw new IllegalArgumentException("Error while updating ong: " + e.getMessage());
        }
    }


    @Override
    public boolean handleDeleteOng(Long id) {
        Optional<Ong> ong = ongRepository.findById(id);
        if (ong.isPresent()) {
            ongRepository.delete(ong.get());
            return true;
        } else {
            return false;
        }
    }
}

