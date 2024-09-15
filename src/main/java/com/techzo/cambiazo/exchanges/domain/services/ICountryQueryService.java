package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.Country;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllCountriesQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetCountryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ICountryQueryService {
    Optional<Country>handle(GetCountryByIdQuery query);

    List<Country>handle(GetAllCountriesQuery query);
}
