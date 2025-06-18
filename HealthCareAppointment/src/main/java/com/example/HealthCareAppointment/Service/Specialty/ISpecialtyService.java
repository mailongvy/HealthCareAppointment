package com.example.HealthCareAppointment.Service.Specialty;

import java.util.List;

import com.example.HealthCareAppointment.Model.Specialty;

public interface ISpecialtyService {
    public List<Specialty> getAllSpecialties();
    public Specialty getSpecialtyById(Long id);
    public Specialty getSpecialtyByName(String name);
    public Specialty addSpecialty(Specialty addSpecialty);
    public Specialty updateSpecialty(Specialty updateSpecialty, Long id);
    public void deleteSpecialty(Long id);

}
