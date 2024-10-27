package com.credit.card.api.repository;

import com.credit.card.api.entity.CreditCardApplication;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for credit card applications
 */
@Repository
public class CreditCardApplicationRepository {

    private final JdbcClient jdbcClient;

    private final RowMapper<CreditCardApplication> rowMapper = (rs, rowNum) -> CreditCardApplication.builder()
            .creditCardApplicationId(rs.getLong("credit_card_application_id"))
            .creditCardApplicantName(rs.getString("credit_card_applicant_name"))
            .creditCardApplicantSurname(rs.getString("credit_card_applicant_surname"))
            .creditCardApplicantId(rs.getString("credit_card_applicant_id"))
            .creditCardApplicationStatus(rs.getString("credit_card_application_status"))
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
        String query = "SELECT credit_card_application_id, credit_card_applicant_name, credit_card_applicant_surname,"
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
        String query = "SELECT credit_card_application_id, credit_card_applicant_name, credit_card_applicant_surname,"
                + " credit_card_applicant_id, credit_card_application_status FROM credit_card_application "
                + " WHERE credit_card_applicant_id = :id ";

        return jdbcClient.sql(query).param("id", id).query(rowMapper).single();
    }
}
