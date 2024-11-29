package com.main.customersvc.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Customer")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    int customerId;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
}
