package com.example.HealthCareAppointment.Service.User;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.User;
import com.example.HealthCareAppointment.Repositories.UserRepository;
import com.example.HealthCareAppointment.Request.LoginRequest;
import com.example.HealthCareAppointment.Security.Service.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    @Autowired
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;
    
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }


    @Override
    public String verify(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        
        // lưu thông tin xác thực vào spring context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // tạo token cho người dùng
        String jwt = jwtService.generateToken(authentication);

       

        // trả về token đã tạo cho user
        return jwt;
    }

    @Override
    public User getAuthenticationUser() {
        // TODO Auto-generated method stub
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }


    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    

}
