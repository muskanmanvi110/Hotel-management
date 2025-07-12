package com.example.demo.DTO;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String role;
    private String phoneNumber;
    private List<BookingDTO> bookings = new ArrayList<>();
}
