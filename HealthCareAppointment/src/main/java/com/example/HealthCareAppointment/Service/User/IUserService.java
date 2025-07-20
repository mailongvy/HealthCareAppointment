package com.example.HealthCareAppointment.Service.User;

import com.example.HealthCareAppointment.Model.User;
import com.example.HealthCareAppointment.Request.LoginRequest;

public interface IUserService {
    public User getUserById(Long userId);

    public String verify(LoginRequest loginRequest);

    public User getAuthenticationUser();


    
}
