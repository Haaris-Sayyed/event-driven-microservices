package com.example.customer.service;

import com.example.customer.command.event.CustomerUpdatedEvent;
import com.example.customer.dto.CustomerDto;
import com.example.customer.entity.Customer;

public interface ICustomerService {

    /**
     * @param customerEntity - Customer Object
     */
    void createCustomer(Customer customerEntity);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    CustomerDto fetchCustomer(String mobileNumber);

    /**
     * @param customerUpdatedEvent - customerUpdatedEvent Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent);

    /**
     * @param customerId - Input Customer ID
     * @return boolean indicating if the delete of Customer details is successful or not
     */
    boolean deleteCustomer(String customerId);
}
