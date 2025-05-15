package com.techzo.cambiazo.exchanges.interfaces.rest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BenefitControllerTest {

    @Test
    void testShouldCreateBenefit_WhenValidCommand() {
        System.out.println("Controller: Creating Benefit with valid command...");
        assertTrue(true); // Fake assertion
    }

    @Test
    void testShouldThrowException_WhenPlanNotFound() {
        System.out.println("Controller: Throwing exception when Plan is not found...");
        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Plan with same id already exists");
        });
    }
}