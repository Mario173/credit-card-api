package com.credit.card.api.service;

import com.credit.card.api.AbstractUnitTest;
import com.credit.card.api.entity.CreditCardApplication;
import com.credit.card.api.exception.DuplicateEntryException;
import com.credit.card.api.exception.EntityNotFoundException;
import com.credit.card.api.repository.CreditCardApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the class {@link CreditCardApplicationService}.
 */
class CreditCardApplicationServiceTest extends AbstractUnitTest {

    @InjectMocks
    private CreditCardApplicationService creditCardApplicationService;

    @Mock
    private CreditCardApplicationRepository creditCardApplicationRepository;

    private CreditCardApplication creditCardApplication;
    private List<CreditCardApplication> creditCardApplications;

    /**
     * Initializes the test data.
     */
    @BeforeEach
    void init() {
        creditCardApplication = CreditCardApplication.builder()
                .creditCardApplicantId("12345678901")
                .creditCardApplicantName("John")
                .creditCardApplicantSurname("Doe")
                .creditCardApplicationStatus("APPROVED")
                .build();

        creditCardApplications = Collections.singletonList(creditCardApplication);
    }

    /**
     * Test for {@link CreditCardApplicationService#getCreditCardApplications()}.
     * Method works correctly when the repository returns a list of credit card applications.
     */
    @Test
    void testGetCreditCardApplicationsWorksCorrectly() {
        when(creditCardApplicationRepository.getCreditCardApplications()).thenReturn(creditCardApplications);

        List<CreditCardApplication> result = creditCardApplicationService.getCreditCardApplications();

        verify(creditCardApplicationRepository, times(1)).getCreditCardApplications();
        assertEquals(creditCardApplications, result);
    }

    /**
     * Test for {@link CreditCardApplicationService#getCreditCardApplicationByPersonalId(String)}.
     * Method works correctly when the ID is valid and applications exist.
     */
    @Test
    void testGetCreditCardApplicationsByPersonalIdWorksCorrectlyWhenIdIsValidAndApplicationsExist() {
        when(creditCardApplicationRepository.getCreditCardApplicationByPersonalId("12345678901"))
                .thenReturn(creditCardApplication);

        CreditCardApplication result = creditCardApplicationService.getCreditCardApplicationByPersonalId("12345678901");

        verify(creditCardApplicationRepository, times(1))
                .getCreditCardApplicationByPersonalId("12345678901");
        assertEquals(creditCardApplication, result);
    }

    /**
     * Test for {@link CreditCardApplicationService#getCreditCardApplicationByPersonalId(String)}.
     * Method works correctly when the ID is valid and applications do not exist.
     */
    @Test
    void testGetCreditCardApplicationsByPersonalIdWorksCorrectlyWhenIdIsValidAndApplicationsDoNotExist() {
        when(creditCardApplicationRepository.getCreditCardApplicationByPersonalId("12345678901"))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(
                EntityNotFoundException.class,
                () -> creditCardApplicationService.getCreditCardApplicationByPersonalId("12345678901")
        );

        verify(creditCardApplicationRepository, times(1))
                .getCreditCardApplicationByPersonalId("12345678901");
    }

    /**
     * Test for {@link CreditCardApplicationService#getCreditCardApplicationByPersonalId(String)}.
     * Method works correctly when the ID is empty.
     */
    @Test
    void testGetCreditCardApplicationsByPersonalIdWorksCorrectlyWhenIdIsEmpty() {
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> creditCardApplicationService.getCreditCardApplicationByPersonalId("")
        );

        verifyNoInteractions(creditCardApplicationRepository);
        assertEquals("Personal ID is null or empty.", illegalArgumentException.getMessage());
    }

    /**
     * Test for {@link CreditCardApplicationService#getCreditCardApplicationByPersonalId(String)}.
     * Method works correctly when the ID has the wrong length.
     */
    @Test
    void testGetCreditCardApplicationsByPersonalIdWorksCorrectlyWhenIdHasWrongLength() {
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> creditCardApplicationService.getCreditCardApplicationByPersonalId("1234")
        );

        verifyNoInteractions(creditCardApplicationRepository);
        assertEquals("Personal ID is not 11 characters long.", illegalArgumentException.getMessage());
    }

    /**
     * Test for {@link CreditCardApplicationService#getCreditCardApplicationByPersonalId(String)}.
     * Method works correctly when the ID has characters that are not digits.
     */
    @Test
    void testGetCreditCardApplicationsByPersonalIdWorksCorrectlyWhenIdHasNonDigitChars() {
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> creditCardApplicationService.getCreditCardApplicationByPersonalId("1234567890a")
        );

        verifyNoInteractions(creditCardApplicationRepository);
        assertEquals("Personal ID contains non-numeric characters.", illegalArgumentException.getMessage());
    }

    /**
     * Test for {@link CreditCardApplicationService#addCreditCardApplication(CreditCardApplication)}.
     * Method works correctly when the repository returns the added credit card application.
     */
    @Test
    void testAddCreditCardApplicationWorksCorrectly() {
        when(creditCardApplicationRepository.addCreditCardApplication(creditCardApplication))
                .thenReturn(creditCardApplication);

        CreditCardApplication result = creditCardApplicationService.addCreditCardApplication(creditCardApplication);

        verify(creditCardApplicationRepository, times(1))
                .addCreditCardApplication(creditCardApplication);
        assertEquals(creditCardApplication, result);
    }

    /**
     * Test for {@link CreditCardApplicationService#addCreditCardApplication(CreditCardApplication)}.
     * Method works correctly when the entry already exists.
     */
    @Test
    void testAddCreditCardApplicationWorksCorrectlyWhenEntryAlreadyExists() {
        when(creditCardApplicationRepository.addCreditCardApplication(creditCardApplication))
                .thenThrow(new DuplicateKeyException("Duplicate key"));

        DuplicateEntryException duplicateEntryException = assertThrows(
                DuplicateEntryException.class,
                () -> creditCardApplicationService.addCreditCardApplication(creditCardApplication)
        );

        verify(creditCardApplicationRepository, times(1))
                .addCreditCardApplication(creditCardApplication);
        assertEquals("An entry with the given personal id already exists.", duplicateEntryException.getMessage());
    }

    /**
     * Test for {@link CreditCardApplicationService#updateCreditCardApplicationByPersonalId(String, CreditCardApplication)}.
     * Method works correctly when the repository returns the updated credit card application.
     */
    @Test
    void testUpdateCreditCardApplicationByPersonalIdWorksCorrectly() {
        when(creditCardApplicationRepository.updateCreditCardApplicationByPersonalId(creditCardApplication))
                .thenReturn(creditCardApplication);

        CreditCardApplication result =
                creditCardApplicationService.updateCreditCardApplicationByPersonalId("12345678901", creditCardApplication);

        verify(creditCardApplicationRepository, times(1))
                .updateCreditCardApplicationByPersonalId(creditCardApplication);
        assertEquals(creditCardApplication, result);
    }

    /**
     * Test for {@link CreditCardApplicationService#updateCreditCardApplicationByPersonalId(String, CreditCardApplication)}.
     * Method works correctly when the ID in the path and in the request body do not match.
     */
    @Test
    void testUpdateCreditCardApplicationByPersonalIdWorksCorrectlyWithTheWrongId() {
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> creditCardApplicationService.updateCreditCardApplicationByPersonalId("12345678902", creditCardApplication)
        );

        verifyNoInteractions(creditCardApplicationRepository);
        assertEquals("Personal ID in the path and in the request body do not match.", illegalArgumentException.getMessage());
    }

    /**
     * Test for {@link CreditCardApplicationService#updateCreditCardApplicationByPersonalId(String, CreditCardApplication)}.
     * Method works correctly when no credit card application is found.
     */
    @Test
    void testUpdateCreditCardApplicationByPersonalIdWhenNoCreditCardApplicationIsFound() {
        when(creditCardApplicationRepository.updateCreditCardApplicationByPersonalId(creditCardApplication))
                .thenReturn(null);

        EntityNotFoundException entityNotFoundException = assertThrows(
                EntityNotFoundException.class,
                () -> creditCardApplicationService.updateCreditCardApplicationByPersonalId("12345678901", creditCardApplication)
        );

        verify(creditCardApplicationRepository, times(1))
                .updateCreditCardApplicationByPersonalId(creditCardApplication);
        assertEquals("No credit card application with given personal id was found.", entityNotFoundException.getMessage());
    }

    /**
     * Test for {@link CreditCardApplicationService#deleteCreditCardApplicationByPersonalId(String)}.
     * Method works correctly when the repository returns 1.
     */
    @Test
    void testDeleteCreditCardApplicationByPersonalIdWorksCorrectly() {
        when(creditCardApplicationRepository.deleteCreditCardApplicationByPersonalId("12345678901"))
                .thenReturn(1);

        creditCardApplicationService.deleteCreditCardApplicationByPersonalId("12345678901");

        verify(creditCardApplicationRepository, times(1))
                .deleteCreditCardApplicationByPersonalId("12345678901");
    }

    /**
     * Test for {@link CreditCardApplicationService#deleteCreditCardApplicationByPersonalId(String)}.
     * Method works correctly when no credit card application is found.
     */
    @Test
    void testDeleteCreditCardApplicationByPersonalIdWorksCorrectlyWhenNoCreditCardApplicationIsFound() {
        when(creditCardApplicationRepository.deleteCreditCardApplicationByPersonalId("12345678901"))
                .thenReturn(0);

        EntityNotFoundException entityNotFoundException = assertThrows(
                EntityNotFoundException.class,
                () -> creditCardApplicationService.deleteCreditCardApplicationByPersonalId("12345678901")
        );

        verify(creditCardApplicationRepository, times(1))
                .deleteCreditCardApplicationByPersonalId("12345678901");
        assertEquals("No credit card application with given personal id was found.", entityNotFoundException.getMessage());
    }

}
