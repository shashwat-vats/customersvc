package com.main.customersvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    String message;
    int status;
    LocalDateTime timeStamp;
}
