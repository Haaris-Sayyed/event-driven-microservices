package com.example.gatewayserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerSummaryDto {

    private CustomerDto customer;
    private AccountsDto accounts;
    private LoansDto loans;
    private CardsDto cards;
}
