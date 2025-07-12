package com.example.demo.model;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull(message = "check in date in dates")
    private LocalDate checkInDate;
    
    @Future(message = "check out must be in the future")
    private LocalDate checkOutDate;
    
    @Min(value = 1, message = "number of adults must not be less than one")
    @Column(name = "adults")
    private int noOfAdults;
    
    @Min(value = 0, message = "number of children must not be less than zero")
    @Column(name = "children")
    private int noOfChildren;
    
    @Column(name = "total_guest")
    private int totalNoOfGuest;

    @Column(name = "confirmation_code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void calculateTotalNumberOfGuest(){
        this.totalNoOfGuest = this.noOfAdults + this.noOfChildren;
    }

    public void setNoOfAdults(int Adults){
        noOfAdults = Adults;
        calculateTotalNumberOfGuest();

    }
    public void setNoOfChildren(int Children){
        noOfChildren = Children;
        calculateTotalNumberOfGuest();
    }

    public void setBookingConfirmationCode(String bookingCode){
        bookingConfirmationCode = bookingCode;
    }

    @Override
    public String toString() {
        return "Booking{" +
        "id=" + id +
        ", checkInDate=" + checkInDate +
        ", checkOutDate=" + checkOutDate +
        ", numOfAdults=" + noOfAdults +
        ", numOfChildren=" + noOfChildren +
        ", totalNumOfGuest=" + totalNoOfGuest +
        ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
        '}';
    }
}
