package com.example.demo.service.Interface;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.Response;
import com.example.demo.model.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}