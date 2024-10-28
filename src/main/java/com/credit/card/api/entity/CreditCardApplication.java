package com.credit.card.api.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Credit Card Application Entity
 */
@Builder
@Getter
@ToString
public class CreditCardApplication {

    @NotNull(message = "Applicant name is required")
    private String creditCardApplicantName;

    @NotNull(message = "Applicant surname is required")
    private String creditCardApplicantSurname;

    @NotNull(message = "Applicant ID is required")
    private String creditCardApplicantId;

    @NotNull(message = "Application status is required")
    private Status creditCardApplicationStatus;

}
