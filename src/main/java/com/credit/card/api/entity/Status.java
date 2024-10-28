package com.credit.card.api.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for credit card application status
 */
public enum Status {
    PENDING("Pending"),
    REJECTED("Rejected"),
    APPROVED("Approved"),
    UNDER_REVIEW("Under Review"),
    ON_HOLD("On Hold"),
    CANCELLED("Cancelled"),
    APPROVED_PENDING_ACTIVATION("Approved Pending Activation"),
    EXPIRED("Expired"),
    REQUIRES_FOLLOW_UP("Requires Follow Up");

    private final String value;

    /**
     * Constructor for Status
     *
     * @param value status value
     */
    Status(String value) {
        this.value = value;
    }

    /**
     * Method to get the value of the status
     *
     * @return status value
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Method to get the status from the value
     *
     * @param value status value
     * @return status
     */
    @JsonCreator
    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }

}
