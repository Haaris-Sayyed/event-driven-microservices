package com.example.customer.command.interceptor;

import com.example.customer.command.CreateCustomerCommand;
import com.example.customer.command.DeleteCustomerCommand;
import com.example.customer.command.UpdateCustomerCommand;
import com.example.customer.constants.CustomerConstants;
import com.example.customer.entity.Customer;
import com.example.customer.exception.CustomerAlreadyExistsException;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CustomerCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final CustomerRepository customerRepository;
    @NonNull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@NonNull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if(CreateCustomerCommand.class.equals(command.getPayloadType())){
                CreateCustomerCommand createCustomerCommand = (CreateCustomerCommand) command.getPayload();
                Optional<Customer> optionalCustomer =
                        customerRepository.findByMobileNumberAndActiveSw(createCustomerCommand.getMobileNumber(), true);

                if(optionalCustomer.isPresent()){
                    throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber"
                            + createCustomerCommand.getMobileNumber());
                }
            } else if(UpdateCustomerCommand.class.equals(command.getPayloadType())){
                UpdateCustomerCommand updateCustomerCommand = (UpdateCustomerCommand) command.getPayload();
                Customer customer = customerRepository.findByMobileNumberAndActiveSw(updateCustomerCommand.getMobileNumber(), true)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", updateCustomerCommand.getMobileNumber()));
            } else if(DeleteCustomerCommand.class.equals(command.getPayloadType())){
                DeleteCustomerCommand deleteCustomerCommand = (DeleteCustomerCommand) command.getPayload();
                Customer customer = customerRepository.findByCustomerIdAndActiveSw(deleteCustomerCommand.getCustomerId(), CustomerConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", deleteCustomerCommand.getCustomerId()));

            }
            return command;
        };
    }
}
