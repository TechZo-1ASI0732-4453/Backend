package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.District;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllDistrictsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetDistrictByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IDistrictQueryService {

    Optional<District> handle(GetDistrictByIdQuery query);

    List<District>handle(GetAllDistrictsQuery query);
}
