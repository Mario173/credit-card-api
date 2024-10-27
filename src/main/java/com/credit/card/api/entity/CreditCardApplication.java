package com.credit.card.api.entity;

import lombok.Builder;

/**
 * Credit Card Application Entity
 */
@Builder
public class CreditCardApplication {

    private Long creditCardApplicationId;
    private String creditCardApplicantName;
    private String creditCardApplicantSurname;
    private String creditCardApplicantId;
    private String creditCardApplicationStatus;

}
