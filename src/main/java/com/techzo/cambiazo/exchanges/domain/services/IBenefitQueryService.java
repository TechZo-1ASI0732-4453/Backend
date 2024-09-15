package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllBenefitsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetBenefitByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IBenefitQueryService {

    Optional<Benefit>handle(GetBenefitByIdQuery query);

    List<Benefit>handle(GetAllBenefitsQuery query);
}
