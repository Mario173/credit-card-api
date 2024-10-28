package com.credit.card.api.controller;

import com.credit.card.api.AbstractUnitTest;
import com.credit.card.api.entity.CreditCardApplication;
import com.credit.card.api.entity.NewCardRequest;
import com.credit.card.api.entity.Status;
import com.credit.card.api.mapper.CreditCardApplicationMapper;
import com.credit.card.api.service.CardRequestService;
import com.credit.card.api.service.CreditCardApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the class {@link CreditCardApplicationController}.
 */
class CreditCardApplicationControllerTest extends AbstractUnitTest {

    @InjectMocks
    private CreditCardApplicationController creditCardApplicationController;

    @Mock
    private CreditCardApplicationService creditCardApplicationService;
    @Mock
    private CardRequestService cardRequestService;
    @Mock
    private CreditCardApplicationMapper creditCardApplicationMapper;

    private CreditCardApplication creditCardApplication;
    private List<CreditCardApplication> creditCardApplications;
    private NewCardRequest newCardRequest;

    /**
     * Initializes the test data.
     */
    @BeforeEach
    void init() {
        creditCardApplication = CreditCardApplication.builder()
                .creditCardApplicantId("12345678901")
                .creditCardApplicantName("John")
                .creditCardApplicantSurname("Doe")
                .creditCardApplicationStatus(Status.APPROVED)
                .build();

        creditCardApplications = Collections.singletonList(creditCardApplication);

        newCardRequest = NewCardRequest.builder()
                .oib("12345678901")
                .firstName("John")
                .lastName("Doe")
                .status(Status.APPROVED)
                .build();
    }

    /**
     * Test for {@link CreditCardApplicationController#getCreditCardApplications()}.
     * The method should return a list of credit card applications.
     */
    @Test
    void testGetCreditCardApplicationsWorksCorrectly() {
        when(creditCardApplicationService.getCreditCardApplications()).thenReturn(creditCardApplications);

        ResponseEntity<List<CreditCardApplication>> response = creditCardApplicationController.getCreditCardApplications();

        verify(creditCardApplicationService, times(1)).getCreditCardApplications();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(creditCardApplications, response.getBody());
    }

    /**
     * Test for {@link CreditCardApplicationController#getCreditCardApplicationByPersonalId(String)}.
     * The method should return a credit card application by the personal ID.
     */
    @Test
    void testGetCreditCardApplicationByPersonalIdWorksCorrectly() {
        when(creditCardApplicationService.getCreditCardApplicationByPersonalId("12345678901"))
                .thenReturn(creditCardApplication);

        ResponseEntity<CreditCardApplication> response =
                creditCardApplicationController.getCreditCardApplicationByPersonalId("12345678901");

        verify(creditCardApplicationService, times(1))
                .getCreditCardApplicationByPersonalId("12345678901");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(creditCardApplication, response.getBody());
    }

    /**
     * Test for {@link CreditCardApplicationController#addCreditCardApplication(CreditCardApplication)}.
     * The method should add a credit card application.
     */
    @Test
    void testAddCreditCardApplicationWorksCorrectly() {
        when(creditCardApplicationService.addCreditCardApplication(creditCardApplication))
                .thenReturn(creditCardApplication);

        ResponseEntity<CreditCardApplication> response =
                creditCardApplicationController.addCreditCardApplication(creditCardApplication);

        verify(creditCardApplicationService, times(1))
                .addCreditCardApplication(creditCardApplication);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(creditCardApplication, response.getBody());
    }

    /**
     * Test for {@link CreditCardApplicationController#updateCreditCardApplicationByPersonalId(String, CreditCardApplication)}.
     * The method should update a credit card application by the personal ID.
     */
    @Test
    void testUpdateCreditCardApplicationByPersonalIdWorksCorrectly() {
        when(creditCardApplicationService.updateCreditCardApplicationByPersonalId("12345678901", creditCardApplication))
                .thenReturn(creditCardApplication);

        ResponseEntity<CreditCardApplication> response =
                creditCardApplicationController.updateCreditCardApplicationByPersonalId("12345678901", creditCardApplication);

        verify(creditCardApplicationService, times(1))
                .updateCreditCardApplicationByPersonalId("12345678901", creditCardApplication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(creditCardApplication, response.getBody());
    }

    /**
     * Test for {@link CreditCardApplicationController#deleteCreditCardApplicationByPersonalId(String)}.
     * Method should delete a credit card application by the personal ID.
     */
    @Test
    void testDeleteCreditCardApplicationByPersonalIdWorksCorrectly() {
        doNothing().when(creditCardApplicationService)
                .deleteCreditCardApplicationByPersonalId("12345678901");

        ResponseEntity<Void> response =
                creditCardApplicationController.deleteCreditCardApplicationByPersonalId("12345678901");

        verify(creditCardApplicationService, times(1))
                .deleteCreditCardApplicationByPersonalId("12345678901");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Test for {@link CreditCardApplicationController#findByPersonalIdAndSendCardRequest(String)}.
     * Method should send a card request for a person.
     */
    @Test
    void testFindByPersonalIdAndSendCardRequestWorksCorrectly() {
        when(creditCardApplicationService.getCreditCardApplicationByPersonalId("12345678901"))
                .thenReturn(creditCardApplication);
        when(creditCardApplicationMapper.toNewCardRequest(creditCardApplication)).thenReturn(newCardRequest);
        when(cardRequestService.sendCardRequest(newCardRequest)).thenReturn(ResponseEntity.ok("Test"));

        ResponseEntity<String> response =
                creditCardApplicationController.findByPersonalIdAndSendCardRequest("12345678901");

        verify(creditCardApplicationService, times(1))
                .getCreditCardApplicationByPersonalId("12345678901");
        verify(creditCardApplicationMapper, times(1)).toNewCardRequest(creditCardApplication);
        verify(cardRequestService, times(1)).sendCardRequest(newCardRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test", response.getBody());
    }

}
