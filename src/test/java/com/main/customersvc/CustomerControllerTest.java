package com.main.customersvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.main.customersvc.controller.CustomerController;
import com.main.customersvc.models.Customer;
import com.main.customersvc.models.CustomerEntity;
import com.main.customersvc.models.CustomerResponse;
import com.main.customersvc.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {


    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Customer customer;

    private CustomerResponse customerResponse;

    private CustomerEntity customerEntity;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Cena");
        customer.setDateOfBirth(LocalDate.of(1997,5,12));

        customerEntity = new CustomerEntity(1,customer.getFirstName(), customer.getLastName(), customer.getDateOfBirth());

        customerResponse = new CustomerResponse(1, "Success");
    }

    @Test
    public void testCreateCustomer() throws Exception {
        when(customerService.saveCustomerDetails(Mockito.any(Customer.class))).thenReturn(customerResponse);

        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString((customer))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").exists());

    }

    @Test
    public void testCreateCustomer_BadRequest_FirstName_NUll() throws Exception {

        customer.setFirstName(null);
        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString((customer))))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testCreateCustomer_BadRequest_DOB_Null() throws Exception {
        customer.setDateOfBirth(null);
        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString((customer))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        when(customerService.getCustomerDetailsById(Mockito.anyInt())).thenReturn(customerEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/fetch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Cena"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {

        customer.setLastName("vats");
        customerResponse.setMessage("Updated Successfully");
        when(customerService.updateCustomerDetails(Mockito.any(Customer.class), Mockito.anyInt())).thenReturn(customerResponse);

        mockMvc.perform(put("/customer/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Updated Successfully"));
    }


    @Test
    public void testDeleteCustomer() throws Exception {
        customerResponse.setMessage("Removed Successfully");
        when(customerService.removeCustomerDetails(Mockito.anyInt())).thenReturn(customerResponse);

        mockMvc.perform(delete("/customer/remove/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Removed Successfully"));
    }

}
