package com.example.demo.service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.Response;
import com.example.demo.DTO.UserDTO;
import com.example.demo.exception.OurExceptions;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Interface.IUserService;
import com.example.demo.utils.JWTUtils;
import com.example.demo.utils.Utils;


@Service
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
        try{
            if(user.getRole()==null || user.getRole().isBlank()){
                user.setRole("USER");
            }
            if(userRepository.existsByEmail(user.getEmail())){
                throw new OurExceptions(user.getEmail() + "Already exist");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            User savedUser = userRepository.save(user);
            
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);


        }catch(OurExceptions e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred while user registration" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try{
            
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurExceptions("user not found"));
            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("successful login");
        
        }catch(Exception e){
            
            response.setStatusCode(500);
            response.setMessage("An error occurred while user registration " + e.getMessage());
        
        }
        
        return response;

    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try{
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOs = Utils.mapUserListEntityToUserListDTO(users);
            response.setStatusCode(200);
            response.setMessage("success");
            response.setUserList(userDTOs);
        }
        catch(OurExceptions e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred while getting all users" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId){
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurExceptions("user not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRooms(user);
            response.setStatusCode(200);
            response.setMessage("success");
            response.setUser(userDTO);
        }
        catch(OurExceptions e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred while getting all users" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try{
            userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurExceptions("user not found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("success");
        }
        catch(OurExceptions e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred while getting all users" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurExceptions("user not found"));
            response.setStatusCode(200);
            response.setMessage("success");
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setUser(userDTO);
        }
        catch(OurExceptions e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred while getting all users" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try{
            User user = userRepository.findByEmail(email).orElseThrow(()-> new OurExceptions("user not found"));
            response.setStatusCode(200);
            response.setMessage("success");
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setUser(userDTO);
        }
        catch(OurExceptions e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("An error occurred while getting all users" + e.getMessage());
        }
        return response;
    }
}
