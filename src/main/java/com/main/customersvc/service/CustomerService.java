package com.main.customersvc.service;

import com.main.customersvc.exception.CustomerSvcException;
import com.main.customersvc.exception.ResourceNotFoundException;
import com.main.customersvc.models.Customer;
import com.main.customersvc.models.CustomerEntity;
import com.main.customersvc.models.CustomerResponse;
import com.main.customersvc.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse saveCustomerDetails(Customer customer) {

        try {

            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setFirstName(customer.getFirstName());
            customerEntity.setLastName(customer.getLastName());
            customerEntity.setDateOfBirth(customer.getDateOfBirth());

            customerEntity = customerRepository.save(customerEntity);

            return new CustomerResponse(customerEntity.getCustomerId(), "Created Successfully");

        } catch (Exception exception) {
            log.error("Exception Occurred while saving customer details: {}", exception.getMessage());
            throw new CustomerSvcException("Exception Occurred while saving customer details");
        }

    }


    public CustomerEntity getCustomerDetailsById(int customerId) {
        Optional<CustomerEntity> customerResponse;
        try {

            customerResponse = customerRepository.findById(customerId);

        } catch (Exception e) {
            log.error("Exception Occurred while fetching customer details: {}", e.getMessage());
            throw new CustomerSvcException("Exception Occurred while fetching customer details");
        }
        return customerResponse.orElseThrow(() -> new ResourceNotFoundException("Requested customerId is not in database"));

    }


    @Transactional
    public CustomerResponse updateCustomerDetails(Customer customer, int customerId) {
        try {
            Optional<CustomerEntity> existingCustomerEntity = customerRepository.findById(customerId);
            CustomerEntity customerEntity;
            if(existingCustomerEntity.isPresent()) {
                customerEntity = updateCustomerEntity(existingCustomerEntity.get(), customer);
                customerRepository.save(customerEntity);
            } else {
                throw new ResourceNotFoundException("Requested customerId is not available in database");
            }
        } catch (CustomerSvcException exception) {
            log.error("Exception Occurred while updating customer details: {}", exception.getMessage());
            throw new CustomerSvcException("Exception Occurred while updating customer details: "+exception.getMessage());
        }
        return new CustomerResponse(customerId, "Updated Successfully");
    }


    @Transactional
    public CustomerResponse removeCustomerDetails( int customerId) {
        try {
            Optional<CustomerEntity> existingCustomerEntity = customerRepository.findById(customerId);
            if(existingCustomerEntity.isPresent()) {
                customerRepository.delete(existingCustomerEntity.get());
            } else {
                throw new ResourceNotFoundException("Requested customerId is not available in database");
            }
        } catch (CustomerSvcException exception) {
            log.error("Exception Occurred while removing customer details: {}", exception.getMessage());
            throw new CustomerSvcException("Exception Occurred while removing customer details");
        }
        return new CustomerResponse(customerId, "Removed Successfully");
    }


    private CustomerEntity updateCustomerEntity(CustomerEntity existingCustomerEntity, Customer customer) {
        if(null!=customer.getFirstName()) {
            existingCustomerEntity.setFirstName(customer.getFirstName());
        }
        if(null!=customer.getFirstName()) {
            existingCustomerEntity.setLastName(customer.getLastName());
        }
        if(null!=customer.getFirstName()) {
            existingCustomerEntity.setDateOfBirth(customer.getDateOfBirth());
        }
        return existingCustomerEntity;
    }
}
