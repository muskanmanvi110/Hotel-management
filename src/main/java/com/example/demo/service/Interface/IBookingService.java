package com.example.demo.service.Interface;

import com.example.demo.DTO.Response;
import com.example.demo.model.Booking;

public interface IBookingService {
    
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}
