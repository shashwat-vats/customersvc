package com.main.customersvc.controller;

import com.main.customersvc.models.Customer;
import com.main.customersvc.models.CustomerEntity;
import com.main.customersvc.models.CustomerResponse;
import com.main.customersvc.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> addCustomerDetails(@Valid @RequestBody Customer customer) {
        CustomerResponse response = customerService.saveCustomerDetails(customer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/fetch/{customerId}")
    public ResponseEntity<CustomerEntity> fetchCustomerDetails(@PathVariable int customerId) {

        CustomerEntity response = customerService.getCustomerDetailsById(customerId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Extra feature added to update and delete customer details

    @PutMapping("/update/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomerDetails(@PathVariable int customerId, @RequestBody Customer customer) {
        CustomerResponse response = customerService.updateCustomerDetails(customer, customerId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{customerId}")
    public ResponseEntity<CustomerResponse> removeCustomerDetails(@PathVariable int customerId) {
        CustomerResponse response = customerService.removeCustomerDetails(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
