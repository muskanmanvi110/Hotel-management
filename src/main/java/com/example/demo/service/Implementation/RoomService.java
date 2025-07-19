package com.example.demo.service.Implementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.Response;
import com.example.demo.DTO.RoomDTO;
import com.example.demo.exception.OurExceptions;
import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.Interface.IRoomService;
import com.example.demo.utils.Utils;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CloudinaryService cloudinaryService; 

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();
        try{

            String imageUrl = cloudinaryService.uploadImage(photo);
            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomDescription(description);
            room.setRoomPrice(roomPrice);
            room.setRoomType(roomType);

            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("success");
            response.setRoom(roomDTO);

        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred" + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        List<String> roomTypeList = roomRepository.findAllDistinctRoomsTypes();
        return roomTypeList;
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try{
            List<Room> roomList = roomRepository.findAll();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("success");
            response.setRoomList(roomDTOList);
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try{
            roomRepository.findById(roomId).orElseThrow(()-> new OurExceptions("room not found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("success");
        }
        catch(OurExceptions e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
                Response response = new Response();
        try{
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new OurExceptions("room not found"));
            String imageURL = null;
            if(photo != null && !photo.isEmpty()){
                imageURL = cloudinaryService.uploadImage(photo);
            }
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setRoomDescription(description);
            if (imageURL != null) room.setRoomPhotoUrl(imageURL);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("success");
            response.setRoom(roomDTO);
        }
        catch(OurExceptions e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurExceptions("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (OurExceptions e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try {
            List<Room> availableRooms = roomRepository.findAvailableRooms(roomType, checkInDate, checkOutDate);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }
}