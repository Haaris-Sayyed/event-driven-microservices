package com.example.customer.command.event;

import lombok.Data;

/**
 * NOUN+VERB(Past Tense)+Event
 */
@Data
public class CustomerCreatedEvent {
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;
}
