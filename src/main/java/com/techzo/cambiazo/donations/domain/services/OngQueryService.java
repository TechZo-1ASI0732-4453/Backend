package com.techzo.cambiazo.donations.domain.services;



import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllOngsQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetOngByIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetOngByLettersQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetOngsByCategoryOngIdQuery;

import java.util.List;
import java.util.Optional;

public interface OngQueryService {
    Optional<Ong> handle(GetOngByIdQuery query);
    List<Ong> handle(GetAllOngsQuery query);

    List<Ong>handle(GetOngsByCategoryOngIdQuery query);

    List<Ong>handle(GetOngByLettersQuery query);

    Optional<Ong> getOngWithRelations(Long id);

}
