package com.techzo.cambiazo.donations.domain.model.valueobjects;

public record SocialNetworkName(String name) {
    public SocialNetworkName {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("The name is required.");
        }
    }

    public String getSocialNetworkName(){
        return name;
    }
}
