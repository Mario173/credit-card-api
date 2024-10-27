package com.credit.card.api.repository;

import com.credit.card.api.AbstractUnitTest;
import com.credit.card.api.entity.CreditCardApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.JdbcClient.StatementSpec;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link CreditCardApplicationRepository}.
 */
class CreditCardApplicationRepositoryTest extends AbstractUnitTest {

    @InjectMocks
    private CreditCardApplicationRepository creditCardApplicationRepository;

    @Mock
    private JdbcClient jdbcClient;

    @Mock
    private StatementSpec statementSpec;

    @Mock
    private JdbcClient.MappedQuerySpec mappedQuerySpec;

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

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
    }

    /**
     * Test for {@link CreditCardApplicationRepository#getCreditCardApplications()}.
     * Verifies that the method works correctly and that the repository returns a list of credit card applications.
     */
    @Test
    void testGetCreditCardApplications() {
        when(statementSpec.query(any(RowMapper.class))).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(creditCardApplications);

        List<CreditCardApplication> result = creditCardApplicationRepository.getCreditCardApplications();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(creditCardApplications, result);
    }

    /**
     * Test for {@link CreditCardApplicationRepository#getCreditCardApplicationByPersonalId(String)}.
     * Verifies that the method works correctly and that the repository returns a credit card application
     * by the personal ID.
     */
    @Test
    void testGetCreditCardApplicationByPersonalId() {
        when(statementSpec.param(anyString(), anyString())).thenReturn(statementSpec);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.single()).thenReturn(creditCardApplication);

        CreditCardApplication result = creditCardApplicationRepository.getCreditCardApplicationByPersonalId("12345678901");

        assertNotNull(result);
        assertEquals(creditCardApplication, result);
    }

    /**
     * Test for {@link CreditCardApplicationRepository#addCreditCardApplication(CreditCardApplication)}.
     * Verifies that the method works correctly and that the repository adds a credit card application.
     */
    @Test
    void testAddCreditCardApplication() {
        when(statementSpec.param(anyString(), anyString())).thenReturn(statementSpec);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        keyHolder.getKeyList().add(Map.of("id", 1L));

        when(statementSpec.update(any(KeyHolder.class))).thenReturn(1);

        CreditCardApplication result = creditCardApplicationRepository.addCreditCardApplication(creditCardApplication);

        assertNull(result);
    }

    /**
     * Test for {@link CreditCardApplicationRepository#updateCreditCardApplicationByPersonalId(CreditCardApplication)}.
     * Verifies that the method works correctly and that the repository updates a credit card application by the personal ID.
     */
    @Test
    void testUpdateCreditCardApplicationByPersonalId() {
        when(statementSpec.param(anyString(), anyString())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        CreditCardApplication result = creditCardApplicationRepository.updateCreditCardApplicationByPersonalId(creditCardApplication);

        assertNotNull(result);
        assertEquals(creditCardApplication, result);
    }

    /**
     * Test for {@link CreditCardApplicationRepository#deleteCreditCardApplicationByPersonalId(String)}.
     * Verifies that the method works correctly and that the repository deletes a credit card application by the personal ID.
     */
    @Test
    void testDeleteCreditCardApplicationByPersonalId() {
        when(statementSpec.param(anyString(), anyString())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);

        int result = creditCardApplicationRepository.deleteCreditCardApplicationByPersonalId("12345678901");

        assertEquals(1, result);
    }
}