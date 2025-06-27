package com.example.HealthCareAppointment.Service.Doctor;

import java.util.List;

import com.example.HealthCareAppointment.Model.Doctor;

public interface IDoctorService {
    public List<Doctor> getAllDoctors();
    public Doctor getDoctorById(Long id);
    public List<Doctor> getDoctorBySpecialty(String specialty);
    public Doctor updateDoctor(Doctor updateDoctor, Long id);
    public Doctor addDoctor(Doctor addDoctor);
    public void deleteDoctor(Long id);
}
