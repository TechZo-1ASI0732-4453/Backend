package com.techzo.cambiazo.donations.domain.model.valueobjects;

public record ProjectName(String name) {
    public ProjectName {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("The name is required.");
        }
    }

    public String getProjectName(){
        return name;
    }
}
