package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllBenefitsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetBenefitByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IBenefitQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IBenefitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BenefitQueryServiceImpl implements IBenefitQueryService {

    private final IBenefitRepository benefitRepository;

    public BenefitQueryServiceImpl(IBenefitRepository benefitRepository){
        this.benefitRepository=benefitRepository;
    }


    @Override
    public Optional<Benefit> handle(GetBenefitByIdQuery query) {
        return benefitRepository.findById(query.id());
    }

    @Override
    public List<Benefit> handle(GetAllBenefitsQuery query) {
        return benefitRepository.findAll();
    }
}
