package com.credit.card.api.controller;

import com.credit.card.api.entity.CreditCardApplication;
import com.credit.card.api.mapper.CreditCardApplicationMapper;
import com.credit.card.api.service.CardRequestService;
import com.credit.card.api.service.CreditCardApplicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Credit Card Controller
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/credit/card/applications")
public class CreditCardApplicationController {

    private final CreditCardApplicationService creditCardApplicationService;
    private final CardRequestService cardRequestService;
    private final CreditCardApplicationMapper creditCardApplicationMapper;

    /**
     * Constructor for CreditCardController
     *
     * @param creditCardApplicationService service for handling credit cards
     * @param cardRequestService           service for handling card requests
     * @param creditCardApplicationMapper  mapper for credit card applications
     */
    public CreditCardApplicationController(
            CreditCardApplicationService creditCardApplicationService,
            CardRequestService cardRequestService,
            CreditCardApplicationMapper creditCardApplicationMapper
    ) {
        this.creditCardApplicationService = creditCardApplicationService;
        this.cardRequestService = cardRequestService;
        this.creditCardApplicationMapper = creditCardApplicationMapper;
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
    @GetMapping("/{id}")
    public ResponseEntity<CreditCardApplication> getCreditCardApplicationByPersonalId(@PathVariable String id) {
        log.info("Fetching credit card application by person id: {}", id);
        return ResponseEntity.ok(creditCardApplicationService.getCreditCardApplicationByPersonalId(id));
    }

    /**
     * Method used to add a credit card application
     *
     * @param creditCardApplication credit card application
     * @return credit card application
     */
    @PostMapping
    public ResponseEntity<CreditCardApplication> addCreditCardApplication(@Valid @RequestBody CreditCardApplication creditCardApplication) {
        log.info("Adding credit card application: {}", creditCardApplication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditCardApplicationService.addCreditCardApplication(creditCardApplication));
    }

    /**
     * Method used to update a credit card application
     *
     * @param id                    applicant id
     * @param creditCardApplication credit card application
     * @return credit card application
     */
    @PutMapping("/{id}")
    public ResponseEntity<CreditCardApplication> updateCreditCardApplicationByPersonalId(
            @PathVariable String id,
            @Valid @RequestBody CreditCardApplication creditCardApplication
    ) {
        log.info("Updating credit card application: {}", creditCardApplication);
        return ResponseEntity.ok(creditCardApplicationService.updateCreditCardApplicationByPersonalId(id, creditCardApplication));
    }

    /**
     * Method used to delete a credit card application by the applicant's ID
     *
     * @param id applicant's ID
     * @return response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditCardApplicationByPersonalId(@PathVariable String id) {
        log.info("Deleting credit card application by person id: {}", id);
        creditCardApplicationService.deleteCreditCardApplicationByPersonalId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Method used to send a card request for a person
     *
     * @param id applicant's ID
     * @return response entity
     */
    @PostMapping("/send-card-request/{id}")
    public ResponseEntity<String> findByPersonalIdAndSendCardRequest(@PathVariable String id) {
        log.info("Sending card request for person with id: {}", id);
        CreditCardApplication creditCardApplication =
                creditCardApplicationService.getCreditCardApplicationByPersonalId(id);
        return cardRequestService.sendCardRequest(creditCardApplicationMapper.toNewCardRequest(creditCardApplication));
    }

}
