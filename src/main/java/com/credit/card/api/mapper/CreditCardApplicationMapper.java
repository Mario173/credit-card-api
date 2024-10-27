package com.credit.card.api.mapper;

import com.credit.card.api.entity.CreditCardApplication;
import com.credit.card.api.entity.NewCardRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Credit Card Application Mapper
 */
@Mapper(componentModel = "spring")
public interface CreditCardApplicationMapper {

    CreditCardApplicationMapper INSTANCE = Mappers.getMapper(CreditCardApplicationMapper.class);

    /**
     * Method used to map a credit card application to a new card request
     *
     * @param creditCardApplication credit card application
     * @return new card request
     */
    @Mapping(source = "creditCardApplicantName", target = "firstName")
    @Mapping(source = "creditCardApplicantSurname", target = "lastName")
    @Mapping(source = "creditCardApplicantId", target = "oib")
    @Mapping(source = "creditCardApplicationStatus", target = "status")
    NewCardRequest toNewCardRequest(CreditCardApplication creditCardApplication);
}
