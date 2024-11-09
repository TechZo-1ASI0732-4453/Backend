package com.techzo.cambiazo.emails.services.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    private String destination;
    private String subject;
    private String name;
    private String code;
}
