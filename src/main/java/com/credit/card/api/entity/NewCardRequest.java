package com.credit.card.api.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Card Request Entity
 */
@Getter
@Setter
@ToString
public class NewCardRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String status;

    @NotNull
    private String oib;

}