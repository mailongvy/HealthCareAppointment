package com.example.HealthCareAppointment.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Patient;
import com.example.HealthCareAppointment.Repositories.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    // get all patients 
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // get patient by id
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Patient Not Found"));
    }

    // add patient 
    public Patient addPatient(Patient patient) {
        Patient addPatient = createPatient(patient);
        return patientRepository.save(addPatient);
    }

    // create patient to add
    public Patient createPatient(Patient addPatient) {
        return new Patient(
            addPatient.getFullName(),
            addPatient.getEmail(),
            addPatient.getPhoneNumber(),
            addPatient.getDateOfBirth(),
            addPatient.getGender(),
            addPatient.getAddress()
        );
    }

    //update Patient
    public Patient updatePatient(Patient requestPatient, Long id) {
        return patientRepository.findById(id)
                                .map(existingPatient -> {
                                    existingPatient.setFullName(requestPatient.getFullName());
                                    existingPatient.setEmail(requestPatient.getEmail());
                                    existingPatient.setPhoneNumber(requestPatient.getPhoneNumber());
                                    existingPatient.setDateOfBirth(requestPatient.getDateOfBirth());
                                    existingPatient.setGender(requestPatient.getGender());
                                    existingPatient.setAddress(requestPatient.getAddress());
                                    return existingPatient;
                                })
                                .map(patientRepository::save)
                                .orElseThrow(() -> new ResourceNotFoundException("Patient Not found"));
    }

    //delete patient by id 
    public void deletePatientById(Long id) {
        patientRepository.findById(id)
                         .ifPresentOrElse(
                             patientRepository::delete,
                             () -> { throw new ResourceNotFoundException("Patient not found"); }
                         );
    }

    
}
