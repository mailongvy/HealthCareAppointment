package com.example.HealthCareAppointment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HealthCareAppointment.Model.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

}
