package com.main.customersvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.main.customersvc.exception.CustomerSvcException;
import com.main.customersvc.exception.ResourceNotFoundException;
import com.main.customersvc.models.Customer;
import com.main.customersvc.models.CustomerEntity;
import com.main.customersvc.models.CustomerResponse;
import com.main.customersvc.repository.CustomerRepository;
import com.main.customersvc.service.CustomerService;
import org.hibernate.exception.JDBCConnectionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTests {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private ObjectMapper objectMapper;

    private Customer customer;

    private CustomerEntity customerEntity;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Cena");
        customer.setDateOfBirth(LocalDate.of(1997, 5, 12));

        customerEntity = new CustomerEntity(1, customer.getFirstName(), customer.getLastName(), customer.getDateOfBirth());

    }

    @Test
    void saveCustomerDetailsTest_201() {

        Mockito.when(customerRepository.save(Mockito.any(CustomerEntity.class))).thenReturn(customerEntity);

        CustomerResponse resp = customerService.saveCustomerDetails(customer);

        Assertions.assertEquals("Created Successfully", resp.getMessage());
    }

    @Test
    void saveCustomerDetailsTest_ConnectionException() {

        Mockito.when(customerRepository.save(Mockito.any(CustomerEntity.class))).thenThrow(JDBCConnectionException.class);
        Assertions.assertThrows(CustomerSvcException.class, () -> {
            CustomerResponse resp = customerService.saveCustomerDetails(customer);
        });
    }


    @Test
    void getCustomerDetailsById_200() {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customerEntity));
        CustomerEntity resp = customerService.getCustomerDetailsById(1);
        Assertions.assertEquals("John", resp.getFirstName());
        Assertions.assertEquals(1, resp.getCustomerId());
    }

    @Test
    void getCustomerDetailsById_404() {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getCustomerDetailsById(1);
        });
    }

    @Test
    void updateCustomerDetailsById_200() {
        customerEntity.setFirstName("Vats");
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customerEntity));
        Mockito.when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        CustomerResponse resp = customerService.updateCustomerDetails(customer,1);
        Assertions.assertEquals(1, resp.getCustomerId());
        Assertions.assertEquals("Updated Successfully", resp.getMessage());
    }

    @Test
    void updateCustomerDetailsById_404() {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            customerService.updateCustomerDetails(customer,1);
        });
    }

    @Test
    void removeCustomerDetailsById_200() {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customerEntity));
        CustomerResponse resp = customerService.removeCustomerDetails(1);
        Assertions.assertEquals(1, resp.getCustomerId());
        Assertions.assertEquals("Removed Successfully", resp.getMessage());
    }

    @Test
    void removeCustomerDetailsById_404() {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            customerService.removeCustomerDetails(1);
        });
    }




}
