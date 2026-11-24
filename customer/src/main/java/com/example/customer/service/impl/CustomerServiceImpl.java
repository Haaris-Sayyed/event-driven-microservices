package com.example.customer.service.impl;

import com.example.customer.command.event.CustomerUpdatedEvent;
import com.example.customer.constants.CustomerConstants;
import com.example.customer.dto.CustomerDto;
import com.example.customer.entity.Customer;
import com.example.customer.exception.CustomerAlreadyExistsException;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;

    @Override
    public void createCustomer(Customer customerEntity) {
        customerEntity.setActiveSw(CustomerConstants.ACTIVE_SW);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndActiveSw(
                customerEntity.getMobileNumber(), true);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerEntity.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customerEntity);
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(customerUpdatedEvent.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", customerUpdatedEvent.getMobileNumber()));
        CustomerMapper.mapEventToCustomer(customerUpdatedEvent, customer);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId)
        );
        customer.setActiveSw(CustomerConstants.IN_ACTIVE_SW);
        customerRepository.save(customer);
        return true;
    }

}
