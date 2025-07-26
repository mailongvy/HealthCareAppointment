package com.example.HealthCareAppointment.Service.User;

import java.util.List;

import com.example.HealthCareAppointment.Model.User;
import com.example.HealthCareAppointment.Request.LoginRequest;

public interface IUserService {
    public List<User> getAllUsers();

    public User getUserById(Long userId);

    public String verify(LoginRequest loginRequest);

    public User getAuthenticationUser();


    
}
