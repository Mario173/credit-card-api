package com.credit.card.api.service;

import com.credit.card.api.entity.CreditCardApplication;
import com.credit.card.api.exception.DuplicateEntryException;
import com.credit.card.api.exception.EntityNotFoundException;
import com.credit.card.api.repository.CreditCardApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Credit Card Service
 */
@Slf4j
@Service
public class CreditCardApplicationService {

    private final CreditCardApplicationRepository creditCardApplicationRepository;

    /**
     * Constructor for CreditCardService
     *
     * @param creditCardApplicationRepository repository for credit card applications
     */
    public CreditCardApplicationService(CreditCardApplicationRepository creditCardApplicationRepository) {
        this.creditCardApplicationRepository = creditCardApplicationRepository;
    }

    /**
     * Method used to get the list containing all credit card applications
     *
     * @return list of credit card applications
     */
    public List<CreditCardApplication> getCreditCardApplications() {
        return creditCardApplicationRepository.getCreditCardApplications();
    }

    /**
     * Method used to get a credit card application by the applicant's ID
     *
     * @param id applicant's ID
     * @return credit card application
     */
    public CreditCardApplication getCreditCardApplicationByPersonalId(String id) {
        validatePersonalId(id);

        try {
            return creditCardApplicationRepository.getCreditCardApplicationByPersonalId(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("No credit card application with given personal ID was found.");
            throw new EntityNotFoundException("No credit card application with given personal id was found.");
        }
    }

    /**
     * Method used to add a credit card application
     *
     * @param creditCardApplication credit card application to add
     * @return added credit card application
     */
    public CreditCardApplication addCreditCardApplication(CreditCardApplication creditCardApplication) {
        validatePersonalId(creditCardApplication.getCreditCardApplicantId());

        try {
            return creditCardApplicationRepository.addCreditCardApplication(creditCardApplication);
        } catch (DuplicateKeyException e) {
            log.error("Failed to add credit card application.");
            throw new DuplicateEntryException("An entry with the given personal id already exists.");
        }
    }

    /**
     * Method used to update a credit card application
     *
     * @param creditCardApplication credit card application to update
     * @return updated credit card application
     */
    public CreditCardApplication updateCreditCardApplicationByPersonalId(String id, CreditCardApplication creditCardApplication) {
        validatePersonalId(creditCardApplication.getCreditCardApplicantId());
        if (!id.equals(creditCardApplication.getCreditCardApplicantId())) {
            log.error("Personal ID in the path and in the request body do not match.");
            throw new IllegalArgumentException("Personal ID in the path and in the request body do not match.");
        }

        CreditCardApplication updatedCreditCardApplication =
                creditCardApplicationRepository.updateCreditCardApplicationByPersonalId(creditCardApplication);
        if (updatedCreditCardApplication == null) {
            log.error("No credit card application with given personal ID was found.");
            throw new EntityNotFoundException("No credit card application with given personal id was found.");
        }

        return updatedCreditCardApplication;
    }

    /**
     * Method used to delete a credit card application by the applicant's ID
     *
     * @param id applicant's ID
     */
    public void deleteCreditCardApplicationByPersonalId(String id) {
        validatePersonalId(id);

        int numberOfDeletedRecords = creditCardApplicationRepository.deleteCreditCardApplicationByPersonalId(id);

        if (numberOfDeletedRecords == 0) {
            log.error("No credit card application with given personal ID was found.");
            throw new EntityNotFoundException("No credit card application with given personal id was found.");
        }
    }

    /**
     * Method used to validate the personal ID
     *
     * @param id personal ID
     */
    private void validatePersonalId(String id) {
        if (id == null || id.isEmpty()) {
            log.error("Personal ID is null or empty.");
            throw new IllegalArgumentException("Personal ID is null or empty.");
        }

        id = id.trim();

        if (id.length() != 11) {
            log.error("Personal ID is not 11 characters long.");
            throw new IllegalArgumentException("Personal ID is not 11 characters long.");
        }

        if (!id.matches("[0-9]+")) {
            log.error("Personal ID contains non-numeric characters.");
            throw new IllegalArgumentException("Personal ID contains non-numeric characters.");
        }
    }
}
