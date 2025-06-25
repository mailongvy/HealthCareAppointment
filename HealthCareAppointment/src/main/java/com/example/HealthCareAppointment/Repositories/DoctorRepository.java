package com.example.HealthCareAppointment.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HealthCareAppointment.Model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    public boolean existsBySpecialtyName(String name);
    public List<Doctor> findBySpecialtyName(String name);
    public boolean existsByEmail(String email);
}
