package com.techzo.cambiazo.donations.domain.model.valueobjects;

public record SocialNetworkUrl(String url) {
    public SocialNetworkUrl {
        if(url == null || url.isBlank()){
            throw new IllegalArgumentException("The url is required.");
        }
    }

    public String getSocialNetworkUrl(){
        return url;
    }
}
