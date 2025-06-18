package com.example.HealthCareAppointment.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HealthCareAppointment.Model.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    public boolean existsByName(String name);
    public Specialty findByName(String name);
}
