package com.credit.card.api.repository;

import com.credit.card.api.entity.CreditCardApplication;
import com.credit.card.api.entity.Status;
import jakarta.validation.Valid;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for credit card applications
 */
@Repository
public class CreditCardApplicationRepository {

    private final JdbcClient jdbcClient;

    private final RowMapper<CreditCardApplication> rowMapper = (rs, rowNum) -> CreditCardApplication.builder()
            .creditCardApplicantName(rs.getString("credit_card_applicant_name"))
            .creditCardApplicantSurname(rs.getString("credit_card_applicant_surname"))
            .creditCardApplicantId(rs.getString("credit_card_applicant_id"))
            .creditCardApplicationStatus(Status.fromValue(rs.getString("credit_card_application_status")))
            .build();

    /**
     * Constructor for CreditCardApplicationRepository
     *
     * @param jdbcClient JDBC client
     */
    public CreditCardApplicationRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Method to get the list containing all credit card applications
     *
     * @return list of credit card applications
     */
    public List<CreditCardApplication> getCreditCardApplications() {
        String query = "SELECT credit_card_applicant_name, credit_card_applicant_surname,"
                + " credit_card_applicant_id, credit_card_application_status FROM credit_card_application ";

        return jdbcClient.sql(query).query(rowMapper).list();
    }

    /**
     * Method to get a credit card application by the applicant's ID
     *
     * @param id applicant's ID
     * @return credit card application
     */
    public CreditCardApplication getCreditCardApplicationByPersonalId(String id) {
        String query = "SELECT credit_card_applicant_name, credit_card_applicant_surname,"
                + " credit_card_applicant_id, credit_card_application_status FROM credit_card_application "
                + " WHERE credit_card_applicant_id = :id ";

        return jdbcClient.sql(query).param("id", id).query(rowMapper).single();
    }

    /**
     * Method to add a credit card application
     *
     * @param creditCardApplication credit card application to add
     * @return added credit card application
     */
    public CreditCardApplication addCreditCardApplication(CreditCardApplication creditCardApplication) {
        String query = "INSERT INTO credit_card_application (credit_card_applicant_name, credit_card_applicant_surname,"
                + " credit_card_applicant_id, credit_card_application_status) VALUES (:name, :surname, :id, :status) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(query)
                .param("name", creditCardApplication.getCreditCardApplicantName())
                .param("surname", creditCardApplication.getCreditCardApplicantSurname())
                .param("id", creditCardApplication.getCreditCardApplicantId())
                .param("status", creditCardApplication.getCreditCardApplicationStatus().getValue())
                .update(keyHolder);

        if (keyHolder.getKey() == null) {
            return null;
        }

        Long creditCardApplicationId = keyHolder.getKey().longValue();
        return CreditCardApplication.builder()
                .creditCardApplicantName(creditCardApplication.getCreditCardApplicantName())
                .creditCardApplicantSurname(creditCardApplication.getCreditCardApplicantSurname())
                .creditCardApplicantId(creditCardApplication.getCreditCardApplicantId())
                .creditCardApplicationStatus(creditCardApplication.getCreditCardApplicationStatus())
                .build();
    }

    /**
     * Method to update a credit card application
     *
     * @param creditCardApplication credit card application to update
     * @return updated credit card application
     */
    public CreditCardApplication updateCreditCardApplicationByPersonalId(@Valid CreditCardApplication creditCardApplication) {
        String query = "UPDATE credit_card_application SET credit_card_applicant_name = :name, "
                + " credit_card_applicant_surname = :surname, credit_card_application_status = :status "
                + " WHERE credit_card_applicant_id = :id ";

        int numberUpdated = jdbcClient.sql(query)
                .param("name", creditCardApplication.getCreditCardApplicantName())
                .param("surname", creditCardApplication.getCreditCardApplicantSurname())
                .param("status", creditCardApplication.getCreditCardApplicationStatus().getValue())
                .param("id", creditCardApplication.getCreditCardApplicantId())
                .update();

        return numberUpdated == 1 ? creditCardApplication : null;
    }

    /**
     * Method to delete a credit card application by the applicant's ID
     *
     * @param id applicant's ID
     * @return number of deleted records
     */
    public int deleteCreditCardApplicationByPersonalId(String id) {
        String query = "DELETE FROM credit_card_application WHERE credit_card_applicant_id = :id ";

        return jdbcClient.sql(query).param("id", id).update();
    }
}
