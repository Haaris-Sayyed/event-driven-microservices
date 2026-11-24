package com.example.customer.query.handler;

import com.example.customer.dto.CustomerDto;
import com.example.customer.query.FindCustomerQuery;
import com.example.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerQueryHandler {

    private final ICustomerService iCustomerService;

    @QueryHandler
    public CustomerDto findCustomer(FindCustomerQuery findCustomerQuery){
        return iCustomerService.fetchCustomer(findCustomerQuery.getMobileNumber());
    }
}
