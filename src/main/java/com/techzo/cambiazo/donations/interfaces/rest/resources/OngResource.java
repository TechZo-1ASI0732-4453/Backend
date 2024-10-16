package com.techzo.cambiazo.donations.interfaces.rest.resources;

public record OngResource(
        Long id,
        String name,
        String type,
        String aboutUs,
        String missionAndVision,
        String supportForm,
        String address,
        String email,
        String phone,
        String logo,
        String website,
        Long categoryOngId,
        String schedule
) {
}
