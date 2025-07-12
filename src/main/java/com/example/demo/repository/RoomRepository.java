package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
    
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findAllDistinctRoomsTypes();


    @Query("SELECT r FROM Room r WHERE r.roomType LIKE CONCAT('%', :roomType, '%') " +
       "AND r.id NOT IN (" +
       "SELECT bk.room.id FROM Booking bk " +
       "WHERE bk.checkInDate <= :checkOutDate AND bk.checkOutDate >= :checkInDate)")
    List<Room> findAvailableRooms(String roomType, LocalDate checkInDate, LocalDate checkOutDate);
    
    
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
    List<Room> getAllAvailableRooms();
}
