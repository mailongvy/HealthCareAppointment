package com.example.HealthCareAppointment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HealthCareAppointment.Model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    public boolean existsByUserEmail(String email);
    public Patient findByUserEmail(String email);

}
