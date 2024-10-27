package com.credit.card.api.controller;

import com.credit.card.api.entity.CreditCardApplication;
import com.credit.card.api.service.CreditCardApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Credit Card Controller
 */
@Slf4j
@RestController
@RequestMapping("/credit/card/applications")
public class CreditCardApplicationController {

    private final CreditCardApplicationService creditCardApplicationService;

    /**
     * Constructor for CreditCardController
     *
     * @param creditCardApplicationService service for handling credit cards
     */
    public CreditCardApplicationController(CreditCardApplicationService creditCardApplicationService) {
        this.creditCardApplicationService = creditCardApplicationService;
    }

    /**
     * Method used to get the list containing all credit card applications
     *
     * @return list of credit card applications
     */
    @GetMapping
    public ResponseEntity<List<CreditCardApplication>> getCreditCardApplications() {
        log.info("Fetching all credit card applications.");
        return ResponseEntity.ok(creditCardApplicationService.getCreditCardApplications());
    }

    /**
     * Method used to get a credit card application by the applicant's ID
     *
     * @param id applicant's ID
     * @return credit card application
     */
    @GetMapping("/personal-id/{id}")
    public ResponseEntity<CreditCardApplication> getCreditCardApplicationByPersonalId(@PathVariable String id) {
        log.info("Fetching credit card application by person id: {}", id);
        return ResponseEntity.ok(creditCardApplicationService.getCreditCardApplicationByPersonalId(id));
    }

}
