package com.main.customersvc.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Customer {

    @NotNull(message = "First name cannot be null")
    String firstName;
    String lastName;
    @NotNull(message = "Date of Birth cannot be null")
    LocalDate dateOfBirth;
}
