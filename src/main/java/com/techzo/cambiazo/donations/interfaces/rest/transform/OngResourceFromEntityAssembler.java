package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.interfaces.rest.resources.OngResource;

public class OngResourceFromEntityAssembler {
    public static OngResource toResourceFromEntity(Ong entity){
        return new OngResource(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getAboutUs(),
                entity.getMissionAndVision(),
                entity.getSupportForm(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getLogo(),
                entity.getWebsite(),
                entity.getCategoryOngId(),
                entity.getSchedule()
        );
    }
}
