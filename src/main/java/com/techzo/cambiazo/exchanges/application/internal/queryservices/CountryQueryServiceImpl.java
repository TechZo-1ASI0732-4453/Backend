package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Country;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllCountriesQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetCountryByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.ICountryQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.ICountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryQueryServiceImpl implements ICountryQueryService {

    private final ICountryRepository countryRepository;

    public CountryQueryServiceImpl(ICountryRepository countryRepository){
        this.countryRepository=countryRepository;
    }

    @Override
    public List<Country> handle(GetAllCountriesQuery query){
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> handle(GetCountryByIdQuery query) {
        return countryRepository.findById(query.id());
    }
}
