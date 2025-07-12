package com.example.demo.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private long ID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int noOfAdults;
    private int noOfChildren;
    private int totalNoOfGuest;
    private String bookingConfirmationCode;
    private UserDTO user;
    private RoomDTO room;
}
