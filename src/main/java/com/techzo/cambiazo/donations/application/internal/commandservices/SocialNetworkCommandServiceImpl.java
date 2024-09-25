package com.techzo.cambiazo.donations.application.internal.commandservices;

import org.springframework.stereotype.Service;
import com.techzo.cambiazo.donations.domain.exceptions.OngNotFoundException;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.aggregates.SocialNetwork;
import com.techzo.cambiazo.donations.domain.model.commands.CreateSocialNetworkCommand;
import com.techzo.cambiazo.donations.domain.model.valueobjects.SocialNetworkName;
import com.techzo.cambiazo.donations.domain.model.valueobjects.SocialNetworkUrl;
import com.techzo.cambiazo.donations.domain.services.SocialNetworkCommandService;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.OngRepository;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.SocialNetworkRepository;

import java.util.Optional;

@Service
public class SocialNetworkCommandServiceImpl implements SocialNetworkCommandService {

    private final SocialNetworkRepository socialNetworkRepository;

    private final OngRepository ongRepository;

    public SocialNetworkCommandServiceImpl(SocialNetworkRepository socialNetworkRepository, OngRepository ongRepository) {
        this.socialNetworkRepository = socialNetworkRepository;
        this.ongRepository = ongRepository;
    }

    @Override
    public Optional<SocialNetwork> handle(CreateSocialNetworkCommand command) {
        Ong ong = ongRepository.findById(command.ongId())
                .orElseThrow(() -> new OngNotFoundException(command.ongId()));

        var name = new SocialNetworkName(command.name());
        var url = new SocialNetworkUrl(command.url());
        socialNetworkRepository.findByNameAndUrl(name,url).ifPresent(socialNetwork ->{
            throw new IllegalArgumentException("Social Network with name and url already exists");
        });
        var socialNetwork = new SocialNetwork(command, ong);
        socialNetworkRepository.save(socialNetwork);
        return Optional.of(socialNetwork);
    }

    @Override
    public boolean handleDeleteSocialNetwork(Long id) {
        Optional<SocialNetwork> socialNetwork = socialNetworkRepository.findById(id);
        if (socialNetwork.isPresent()) {
            socialNetworkRepository.delete(socialNetwork.get());
            return true;
        } else {
            return false;
        }
    }
}
