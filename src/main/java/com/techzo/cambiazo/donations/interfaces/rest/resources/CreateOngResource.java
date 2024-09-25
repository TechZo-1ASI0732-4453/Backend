package com.techzo.cambiazo.donations.interfaces.rest.resources;

public record CreateOngResource(
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
