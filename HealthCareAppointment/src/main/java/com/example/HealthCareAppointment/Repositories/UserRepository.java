package com.example.HealthCareAppointment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HealthCareAppointment.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
