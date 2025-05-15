package com.techzo.cambiazo;

import com.techzo.cambiazo.exchanges.interfaces.rest.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class ExchangeControllersTests {

    @Autowired
    private ExchangeController exchangeController;

    @Autowired
    private BenefitController benefitController;

    @Autowired
    private CountryController countryController;

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private DistrictController districtController;

    @Autowired
    private ProductController productController;

    @Autowired
    private PlanController planController;

    @Autowired
    private SubscriptionController subscriptionController;

    @Test
    void testGetAllExchanges() {
        ResponseEntity<?> response = exchangeController.getAllExchanges();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllBenefits() {
        ResponseEntity<?> response = benefitController.getAllBenefits();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllCountries() {
        ResponseEntity<?> response = countryController.getAllCountries();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllDepartments() {
        ResponseEntity<?> response = departmentController.getAllDepartments();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllDistricts() {
        ResponseEntity<?> response = districtController.getAllDistricts();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllProducts() {
        ResponseEntity<?> response = productController.getAllProducts();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllPlans() {
        ResponseEntity<?> response = planController.getAllPlans();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllSubscriptions() {
        ResponseEntity<?> response = subscriptionController.getAllSubscriptions();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}