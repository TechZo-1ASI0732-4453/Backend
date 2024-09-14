package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateCountryCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Country;
import com.techzo.cambiazo.exchanges.domain.services.ICountryCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.ICountryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryCommandServiceImpl implements ICountryCommandService {
    private final ICountryRepository countryRepository;


    public CountryCommandServiceImpl(ICountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Optional<Country>handle(CreateCountryCommand command) {
        if (countryRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Country with same name already exists");
        }
        var country = new Country(command);
        var createdCountry = countryRepository.save(country);
        return Optional.of(createdCountry);
    }
}
