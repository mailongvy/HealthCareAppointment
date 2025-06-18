package com.example.HealthCareAppointment.Service.Patient;

import java.util.List;

import com.example.HealthCareAppointment.Model.Patient;

public interface IPatientService {
    public List<Patient> getAllPatients();
    public Patient getPatientById(Long id);
    public Patient addPatient(Patient patient);
    public Patient updatePatient(Patient requestPatient, Long id);
    public void deletePatientById(Long id);

}
