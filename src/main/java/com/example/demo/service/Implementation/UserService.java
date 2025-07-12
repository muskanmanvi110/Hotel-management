package com.example.demo.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.Response;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Interface.IUserService;
import com.example.demo.utils.JWTUtils;

public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Override
    public Response register(User user) {
        Response response = new Response();
        return null;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public Response getAllUsers() {
        return null;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        return null;
    }

    @Override
    public Response deleteUser(String userId) {
        return null;
    }

    @Override
    public Response getUserById(String userId) {
        return null;
    }

    @Override
    public Response getMyInfo(String email) {
        return null;
    }
}
