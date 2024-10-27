package com.credit.card.api.service;

import com.credit.card.api.entity.CreditCardApplication;
import com.credit.card.api.exception.EntityNotFoundException;
import com.credit.card.api.repository.CreditCardApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Credit Card Service
 */
@Slf4j
@Service
public class CreditCardApplicationService {

    private CreditCardApplicationRepository creditCardApplicationRepository;

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
